package com.xnx3.j2ee.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.MessageData;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.MessageDataService;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.DateUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 站内信
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	
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
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/add")
	public String add(HttpServletRequest request ,  HttpServletResponse response,Model model){
		return "message/add";
	}
	
	/**
	 * 发送信息提交后，逻辑处理
	 * @param other
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("messageSend")
	@RequestMapping("/send")
	public String send(
			@RequestParam(value = "id", defaultValue = "0") String other, 
			@RequestParam(value = "content", defaultValue = "") String content, 
			HttpServletRequest request ,  
			HttpServletResponse response,
			Model model){
		int otherId = Integer.parseInt(other);
		if(otherId<1){
			return error(model, "信息发送给谁呢？");
		}
		
		if(content.length()>Global.MESSAGE_CONTENT_MINLENGTH&&content.length()<Global.MESSAGE_CONTENT_MAXLENGTH){
			//正常
		}else{
			return error(model, "信息内容必须在"+Global.MESSAGE_CONTENT_MINLENGTH+"～"+Global.MESSAGE_CONTENT_MAXLENGTH+"之间");
		}
		
		Message message = new Message();
		message.setSelf(getUser().getId());
		message.setOther(otherId);
		message.setTime(DateUtil.timeForUnix10());
		message.setState(Message.MESSAGE_STATE_UNREAD);
		messageService.save(message);
		
		MessageData messageData = new MessageData();
		messageData.setId(message.getId());
		messageData.setContent(content);
		messageDataService.save(messageData);
		
		if(messageData.getId()>0){
			return success(model, "信息发送成功！","message/list.do");
		}else{
			return error(model, "信息发送失败！");
		}
	}
	

	/**
	 * 阅读信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("messageView")
	@RequestMapping("/view")
	public String view(@RequestParam(value = "id", defaultValue = "0") String id, HttpServletRequest request ,  HttpServletResponse response,Model model){
		int messageId = Integer.parseInt(id);
		if(messageId<1){
			return error(model, "传入的信息有误！");
		}
		
		Message message = messageService.findById(messageId);
		if(message!=null){
			int userId = getUser().getId();
			//查看此信息是此人发的，或者是发送给此人的，此人有权限查看
			if(userId==message.getOther()||userId==message.getSelf()){
				//拿到发信人信息
				User user = userService.findById(message.getOther());
				model.addAttribute("sendUser",user);
				
				//拿到信息的内容
				MessageData messageData = messageDataService.findById(messageId);
				
				//如果阅读的人是收信人，且之前没有阅读过，则标注此信息为已阅读
				if(getUser().getId()==message.getOther()&&message.getState()==Message.MESSAGE_STATE_UNREAD){
					message.setState(Message.MESSAGE_STATE_READ);
					messageService.save(message);

					Log log = new Log();
					log.setAddtime(new Date());
					log.setType(Log.typeMap.get("MESSAGE_READ"));
					log.setGoalid(message.getId());
					log.setUserid(getUser().getId());
					log.setValue(messageData.getContent());
					logService.save(log);
				}
				
				model.addAttribute("result","1");
				model.addAttribute("info", "成功！");
				model.addAttribute("message",message);
				model.addAttribute("content",messageData.getContent());
			}else{
				return error(model, "您无权查看此信息！");
			}
		}
		
		return "message/view";
	}
	
	/**
	 * 信息列表
	 * @param type String <li>already：已读信息
	 * 						<li>unread：未读信息
	 * 						<li>all：全部信息
	 * @param box 是发件箱还是收件箱
	 * 						<li>outbox:发件箱
	 * 						<li>inbox:收件箱
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("messageList")
	@RequestMapping("/list")
	public String list(@RequestParam(value = "type", defaultValue = "inboxAll") String type, 
			@RequestParam(value = "box", defaultValue = "inbox") String box, 
			HttpSession session, HttpServletRequest request ,  HttpServletResponse response,Model model){
		Sql sql = new Sql();
		String[] column = {"id","state="};
		String boxWhere = box.equals("inbox")? "other="+getUser().getId():"self="+getUser().getId();
		
		String where = sql.generateWhere(request, column, boxWhere);
		int count = globalService.count("message", where);
		Page page = new Page(count, Global.PAGE_DEFAULT_EVERYNUMBER, request);
		where = sql.generateWhere(request, column, "message.id=message_data.id AND message."+boxWhere,"message");
		List<Map<String, String>> list = globalService.findBySqlQuery("SELECT message.*,message_data.content, (SELECT user.nickname FROM user WHERE user.id=message.other) AS other_nickname ,(SELECT user.nickname FROM user WHERE user.id=message.self) AS self_nickname FROM message ,message_data ,user "+where+" GROUP BY message.id ORDER BY message.id DESC", page);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "message/list";
	}
	
}
