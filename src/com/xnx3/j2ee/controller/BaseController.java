package com.xnx3.j2ee.controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.shiro.ActiveUser;

/**
 * 所有Controller父类
 * @author 管雷鸣
 *
 */
public class BaseController {
	
	/**
	 * 获取用户登录之后的用户相关信息
	 * @return	<li>登陆了，则返回ActiveUser对象
	 * 			<li>未登陆，返回null
	 */
	protected ActiveUser getActiveUser() {
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		if(activeUser != null){
			return activeUser;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取当前登录用户的信息
	 * @return 	<li>登陆了，则返回User对象
	 * 			<li>未登陆，返回null
	 */
	protected User getUser(){
		ActiveUser activeUser = getActiveUser();
		if(activeUser!=null){
			return activeUser.getUser();
		}else{
			return null;
		}
	}

	/**
	 * 更新登录的用户的用户信息
	 * @param user
	 */
	protected void setUserForSession(User user){
		getActiveUser().setUser(user);
	}
	
	/**
	 * 成功提示中转页面
	 * @param model
	 * @param info 提示信息
	 * @param redirectUrl 提示后要跳转至的页面，支持：
	 * 					<li>null	返回之前页面，js的返回上个页面
	 * 					<li>user/info.do	内网页面，前面无须/，默认自动补齐之前路径
	 * 					<li>http://www.xnx3.com	外网页面，写全即可
	 * @return
	 */
	protected String success(Model model , String info , String redirectUrl){
		return prompt(model, info,redirectUrl, Global.PROMPT_STATE_SUCCESS);
	}
	
	/**
	 * 成功提示中转页面
	 * @param model
	 * @param info 提示信息
	 * @return
	 */
	protected String success(Model model , String info){
		return prompt(model, info,null, Global.PROMPT_STATE_SUCCESS);
	}
	
	
	/**
	 * 错误提示中专页面
	 * @param model
	 * @param info 提示的信息
	 * @param redirectUrl 提示后要跳转至的页面，支持：
	 * 					<li>null	返回之前页面，js的返回上个页面
	 * 					<li>user/info.do	内网页面，前面无须/，默认自动补齐之前路径
	 * 					<li>http://www.xnx3.com	外网页面，写全即可
	 * @return
	 */
	protected String error(Model model , String info,String redirectUrl){
		return prompt(model, info,redirectUrl, Global.PROMPT_STATE_ERROR);
	}
	
	/**
	 * 错误提示中专页面
	 * @param model
	 * @param info 提示的信息
	 * @return
	 */
	protected String error(Model model,String info){
		return error(model, info, null);
	}
	
	/**
	 * 提示信息中专页面
	 * @param model org.springframework.ui.Model
	 * @param info 显示的提示信息
	 * @param redirectUrl 提示后要跳转至的页面，支持：
	 * 					<li>null	返回之前页面，js的返回上个页面
	 * 					<li>user/info.do	内网页面，前面无须/，默认自动补齐之前路径
	 * 					<li>http://www.xnx3.com	外网页面，写全即可
	 * @param state 状态，成功提示还是错误提示
	 * 				<li> {@link #PROMPT_STATE_SUCCESS }
	 * 				<li> {@link #PROMPT_STATE_ERROR }
	 * @return
	 */
	private String prompt(Model model , String info,String redirectUrl, int state){
		model.addAttribute("info", info);
		model.addAttribute("state", state);
		
		if(redirectUrl==null||redirectUrl.length()==0){
			redirectUrl = "javascript:history.go(-1);";
		}

		model.addAttribute("redirectUrl", redirectUrl);
		
		return "/prompt";
	}
	
	public static void main(String[] args) {
		
	}
}
