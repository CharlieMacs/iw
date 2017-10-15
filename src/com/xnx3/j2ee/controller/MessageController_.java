package com.xnx3.j2ee.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.ActionLogCache;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.MessageVO;

/**
 * 站内信
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController_ extends BaseController {
	@Resource
	private MessageService messageService;
	@Resource
	private UserService userService;
	@Resource
	private SqlService sqlService;
	
	/**
	 * 填写信息页面
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		ActionLogCache.insert(request, "写信息");
		return "iw/message/add";
	}
	
	/**
	 * 发送信息提交
	 * @param other 发送给的用户id
	 * @param content 发送信息的内容
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/send")
	public String send(HttpServletRequest request,Model model){
		BaseVO baseVO = messageService.sendMessage(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			ActionLogCache.insert(request, "发送信息", "成功");
			return success(model, "信息发送成功！","message/list.do");
		}else{
			ActionLogCache.insert(request, "发送信息", "失败："+baseVO.getInfo());
			return error(model, baseVO.getInfo());
		}
	}
	
	/**
	 * 阅读信息
	 * @param id 信息的id，message.id
	 */
	@RequiresPermissions("messageView")
	@RequestMapping("/view")
	public String view(@RequestParam(value = "id", defaultValue = "0") int id,Model model, HttpServletRequest request){
		MessageVO messageVO = messageService.read(id);
		if(messageVO.getResult() == MessageVO.SUCCESS){
			ActionLogCache.insert(request, "阅读信息", messageVO.getContent());
			
			model.addAttribute("messageVO", messageVO);
			return "iw/message/view";
		}else{
			ActionLogCache.insert(request, "阅读信息", "失败："+messageVO.getInfo());
			return error(model, messageVO.getInfo());
		}
	}
	
	/**
	 * 信息列表
	 * @param type 	<ul> 
	 * 						<li>already：已读信息
	 * 						<li>unread：未读信息
	 * 						<li>all：全部信息
	 * 					</ul>
	 * @param box 是发件箱还是收件箱:
	 * 					<ul>
	 * 						<li>outbox:发件箱
	 * 						<li>inbox:收件箱
	 * 					</ul>
	 */
	@RequiresPermissions("messageList")
	@RequestMapping("/list")
	public String list(@RequestParam(value = "type", defaultValue = "inboxAll") String type, 
			@RequestParam(value = "box", defaultValue = "inbox") String box, 
			HttpServletRequest request,Model model){
		Sql sql = new Sql(request);
		sql.setSearchTable("message");
		sql.setSearchColumn(new String[]{"id","state="});
		String boxWhere = box.equals("inbox")? "message.recipientid="+getUser().getId():"message.senderid="+getUser().getId();
		sql.appendWhere(boxWhere+" AND message.senderid=user.id AND message.isdelete = "+Message.ISDELETE_NORMAL+" AND user.isfreeze="+User.ISFREEZE_NORMAL);
		int count = sqlService.count("message,user", sql.getWhere());
		Page page = new Page(count, Global.getInt("LIST_EVERYPAGE_NUMBER"), request);
		sql.setSelectFromAndPage("SELECT message.*,message_data.content, (SELECT user.nickname FROM user WHERE user.id=message.recipientid) AS other_nickname ,(SELECT user.nickname FROM user WHERE user.id=message.senderid) AS self_nickname FROM message ,message_data ,user ", page);
		sql.setGroupBy("message.id");
		List<Map<String, Object>> list = sqlService.findMapBySql(sql);
		
		ActionLogCache.insert(request, "信息列表", "第"+page.getCurrentPageNumber()+"页");
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "iw/message/list";
	}
}
