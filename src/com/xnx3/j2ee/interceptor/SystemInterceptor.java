package com.xnx3.j2ee.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.shiro.ActiveUser;

/**
 * 站内信息最新未读信息查询
 * @author 管雷鸣
 *
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {
	public static boolean useMessage=false;	//是否使用站内信息功能	
	@Resource
	private MessageService messageService;
	
	static{
		useMessage = ConfigManagerUtil.getSingleton("systemConfig.xml").selectValue("useMessage").equals("true");
	}
	
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		if(activeUser!=null){
			User user=activeUser.getUser();
			
			if(useMessage){
				Message messageWhere=new Message();
				messageWhere.setRecipientid(user.getId());
				messageWhere.setState(Message.MESSAGE_STATE_UNREAD);
				List<Message> list = messageService.findByExample(messageWhere);
				
				if(modelAndView!=null){
					modelAndView.addObject("user", user);
					modelAndView.addObject("messageList", list);
					modelAndView.addObject("unreadMessage", list.size());
				}
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}
	
	
	
}
