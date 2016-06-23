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
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.shiro.ActiveUser;
import com.xnx3.j2ee.util.CookieUtil;

/**
 * 每次请求页面时，都会先通过这个
 * @author 管雷鸣
 *
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {
	public static boolean useMessage=false;	//是否使用站内信息功能	
	@Resource
	private MessageService messageService;
	
	static{
		useMessage = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("message.used").equals("true");
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
			
			//站内信
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
		
		//语言
		//如果是第一次访问，先设定默认语言
		if(request.getSession().getAttribute("language_default") == null){
			String language_default = null;
			CookieUtil cookieUtil = new CookieUtil(request, response);
			if(cookieUtil.getCookie("language_default") != null){
				language_default = cookieUtil.getCookie("language_default").getValue();
			}
			if(language_default == null){
				language_default = Global.language_default;
			}
			
			if(Global.language.get(language_default) != null){
				Global.language_default = language_default;
			}
			request.getSession().setAttribute("language_default", Global.language_default);
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}
}
