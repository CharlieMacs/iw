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
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.MessageDataService;
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
	private MessageDataService messageDataService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private GlobalService globalService;

	@Resource
	private LogService logService;
	
	/**
	 * 填写信息页面
	 * @return View
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/add")
	public String add(){
		return "iw/message/add";
	}
	
	/**
	 * 发送信息提交
	 * @param other 发送给的用户id
	 * @param content 发送信息的内容
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/send")
	public String send(HttpServletRequest request,Model model){
		BaseVO baseVO = messageService.sendMessage(request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			return success(model, "信息发送成功！","message/list.do");
		}else{
			return error(model, baseVO.getInfo());
		}
	}
	

	/**
	 * 阅读信息
	 * @param id 信息的id，message.id
	 * @return View
	 */
	@RequiresPermissions("messageView")
	@RequestMapping("/view")
	public String view(@RequestParam(value = "id", defaultValue = "0") int id,Model model){
		MessageVO messageVO = messageService.read(id);
		if(messageVO.getResult() == MessageVO.SUCCESS){
			model.addAttribute("messageVO", messageVO);
			return "iw/message/view";
		}else{
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
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 * @return View
	 */
	@RequiresPermissions("messageList")
	@RequestMapping("/list")
	public String list(@RequestParam(value = "type", defaultValue = "inboxAll") String type, 
			@RequestParam(value = "box", defaultValue = "inbox") String box, 
			HttpServletRequest request,Model model){
		Sql sql = new Sql();
		String[] column = {"id","state="};
		String boxWhere = box.equals("inbox")? "message.recipientid="+getUser().getId():"message.senderid="+getUser().getId();
		
		String where = sql.generateWhere(request, column, boxWhere+" AND message.senderid=user.id AND message.isdelete = "+Message.ISDELETE_NORMAL+" AND user.isfreeze="+User.ISFREEZE_NORMAL);
		int count = globalService.count("message,user", where);
		Page page = new Page(count, Global.PAGE_DEFAULT_EVERYNUMBER, request);
		where = sql.generateWhere(request, column, "message.senderid=user.id AND message.id=message_data.id AND user.isfreeze="+User.ISFREEZE_NORMAL+" AND "+boxWhere,"message");
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT message.*,message_data.content, (SELECT user.nickname FROM user WHERE user.id=message.recipientid) AS other_nickname ,(SELECT user.nickname FROM user WHERE user.id=message.senderid) AS self_nickname FROM message ,message_data ,user "+where+" GROUP BY message.id ORDER BY message.id DESC", page);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "iw/message/list";
	}
	
}
