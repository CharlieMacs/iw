package com.xnx3.j2ee.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
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
	
	public static boolean useExecuteTime = false;	//是否使用Controller函数记录执行时间的功能
	public static long recordTime = 0;	//若执行时间超过多少毫秒，就在控制台打印出来，这里的单位是毫秒
	
	@Resource
	private MessageService messageService;
	
	static{
		useMessage = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("message.used").equals("true");
		useExecuteTime = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("ExecuteTime.controller.used").equals("true");
		int recordTimeInt = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("ExecuteTime.recordTime"), 0);
		recordTime = recordTimeInt;
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
		if(subject != null){
			//取身份信息
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			if(activeUser!=null){
				User user=activeUser.getUser();
				
				//站内信
//				if(useMessage){
//					Message messageWhere=new Message();
//					messageWhere.setRecipientid(user.getId());
//					messageWhere.setState(Message.MESSAGE_STATE_UNREAD);
//					List<Message> list = messageService.findByExample(messageWhere);
//					
//					if(modelAndView!=null){
//						modelAndView.addObject("user", user);
//						modelAndView.addObject("messageList", list);
//						modelAndView.addObject("unreadMessage", list.size());
//					}
//				}
			}
		}
		
		//语言
//		//如果是第一次访问，先设定默认语言
//		HttpSession session = request.getSession();
//		//判断其是否有Session
//		if(session != null){
//			if(request.getSession().getAttribute("language_default") == null){
//				String language_default = null;
//				CookieUtil cookieUtil = new CookieUtil(request, response);
//				if(cookieUtil.getCookie("language_default") != null){
//					language_default = cookieUtil.getCookie("language_default").getValue();
//				}
//				if(language_default == null){
//					language_default = Global.language_default;
//				}
//				
//				if(Global.language.get(language_default) != null){
//					Global.language_default = language_default;
//				}
//				request.getSession().setAttribute("language_default", Global.language_default);
//			}
//		}
		
		
		if(useExecuteTime){
			long startTime = (Long)request.getAttribute("startTime");  
	        long endTime = System.currentTimeMillis();  
	        long executeTime = endTime - startTime;  
	        
	        if(executeTime > recordTime){
	        	System.out.println(DateUtil.currentDate("MM-dd HH:mm:ss")+" ControllerExecuteTime : "+executeTime+" ms , "+handler);
			}
		}
        
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(useExecuteTime){
			long startTime = System.currentTimeMillis();  
	        request.setAttribute("startTime", startTime);  
		}
		return true;
	}
}
