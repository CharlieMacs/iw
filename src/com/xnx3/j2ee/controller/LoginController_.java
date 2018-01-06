package com.xnx3.j2ee.controller;

import java.awt.Font;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.Func;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.ActionLogCache;
import com.xnx3.j2ee.func.Captcha;
import com.xnx3.j2ee.service.SmsLogService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.media.CaptchaUtil;

/**
 * 登录、注册
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/")
public class LoginController_ extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private SmsLogService smsLogService;
	
	/**
	 * 注册页面 
	 */
	@RequestMapping("/reg")
	public String reg(HttpServletRequest request ,Model model){
		if(Global.getInt("ALLOW_USER_REG") == 0){
			return error(model, "系统已禁止用户自行注册");
		}
		//判断用户是否已注册，已注册的用户将出现提示，已登录，无需注册
		if(getUser() != null){
			return error(model, "您已登陆，无需注册");
		}
		
		userService.regInit(request);
		ActionLogCache.insert(request, "进入注册页面");
		return "iw/login/reg";
	}
	
	/**
	 * 验证码图片显示，直接访问此地址可查看图片
	 */
	@RequestMapping("/captcha")
	public void captcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ActionLogCache.insert(request, "获取验证码显示");
		
		CaptchaUtil captchaUtil = new CaptchaUtil();
	    captchaUtil.setCodeCount(5);                   //验证码的数量，若不增加图宽度的话，只能是1～5个之间
	    captchaUtil.setFont(new Font("Fixedsys", Font.BOLD, 21));    //验证码字符串的字体
	    captchaUtil.setHeight(18);  //验证码图片的高度
	    captchaUtil.setWidth(110);      //验证码图片的宽度
//	    captchaUtil.setCode(new String[]{"我","是","验","证","码"});   //如果对于数字＋英文不满意，可以自定义验证码的文字！
	    Captcha.showImage(captchaUtil, request, response);
	}
	
	/**
	 * 注册相应请求地址
	 * @param user {@link User}
	 */
	@RequestMapping("/regSubmit")
	public String regSubmits(User user ,HttpServletRequest request,Model model){
		if(Global.getInt("ALLOW_USER_REG") == 0){
			return error(model, "系统禁止用户自行注册");
		}
		
		BaseVO baseVO = userService.reg(user, request);
		if(baseVO.getResult() == BaseVO.SUCCESS){
			ActionLogCache.insert(request, "注册提交", "成功，注册的用户名："+getUser().getUsername());
			return "redirect:/login.do";
		}else{
			ActionLogCache.insert(request, "注册提交", "失败");
			return error(model, baseVO.getInfo());
		}
	}
	
	/**
	 * 登陆页面
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request,Model model){
		if(getUser() != null){
			ActionLogCache.insert(request, "进入登录页面", "已经登录成功，无需再登录，进行跳转");
			return redirect("user/index.do");
		}
		
		ActionLogCache.insert(request, "进入登录页面");
		return "iw/login/login";
	}

	/**
	 * 登陆请求验证
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交三个参数：username(用户名/邮箱)、password(密码)、code（图片验证码的字符）
	 */
	@RequestMapping("loginSubmit")
	@ResponseBody
	public BaseVO loginSubmit(HttpServletRequest request,Model model){
		//验证码校验
		BaseVO capVO = Captcha.compare(request.getParameter("code"), request);
		if(capVO.getResult() == BaseVO.FAILURE){
			ActionLogCache.insert(request, "用户名密码模式登录失败", "验证码出错，提交的验证码："+StringUtil.filterXss(request.getParameter("code")));
			return capVO;
		}else{
			//验证码校验通过
			
			BaseVO baseVO =  userService.loginByUsernameAndPassword(request);
			if(baseVO.getResult() == BaseVO.SUCCESS){
				//登录成功,BaseVO.info字段将赋予成功后跳转的地址，所以这里要再进行判断
				
				//得到当前登录的用户的信息
				User user = getUser();
				//可以根据用户的不同权限，来判断用户登录成功后要跳转到哪个页面
				if(Func.isAuthorityBySpecific(user.getAuthority(), Global.get("ROLE_SUPERADMIN_ID"))){
					//如果是超级管理员，那么跳转到管理后台
					baseVO.setInfo("admin/index/index.do");
					ActionLogCache.insert(request, "用户名密码模式登录成功","进入管理后台admin/index/");
				}else{
					//其他的默认为普通用户。若有其他权限，可以在此，如同超级管理员一般，继续判断
					baseVO.setInfo("user/index.do");
					ActionLogCache.insert(request, "用户名密码模式登录成功","进入普通用户首页");
				}
			}else{
				ActionLogCache.insert(request, "用户名密码模式登录失败",baseVO.getInfo());
			}
			
			return baseVO;
		}
	}

	/**
	 * 手机号－验证码方式登陆
	 */
	@RequestMapping("phoneVerifyLogin")
	public String phoneVerifyLogin(HttpServletRequest request){
		if(getUser() != null){
			ActionLogCache.insert(request, "进入手机号验证码模式登录页面","已登录，无需再登录了，直接跳转");
			return redirect("user/index.do");
		}
		
		ActionLogCache.insert(request, "进入手机号验证码模式登录页面");
		return "iw/login/phoneVerifyLogin";
	}
	
	/**
	 * 登陆请求验证
	 * @param phone 登录的手机号
	 * @param code 发送到手机的验证码
	 * @return {@link BaseVO}
	 */
	@RequestMapping("phoneVerifyLoginSubmit")
	@ResponseBody
	public BaseVO loginByPhoneAndCode(HttpServletRequest request){
		BaseVO baseVO = userService.loginByPhoneAndCode(request);
		if(baseVO.getResult() - BaseVO.SUCCESS == 0){
			ActionLogCache.insert(request, "手机号验证码模式登录成功");
		}else{
			ActionLogCache.insert(request, "手机号验证码模式登录失败", baseVO.getInfo());
		}
		return baseVO;
	}
	
	/**
	 * 发送手机号登录的验证码
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单需提交参数：phone(发送到的手机号)
	 * @return {@link BaseVO}
	 */
	@RequestMapping("sendPhoneLoginCode")
	@ResponseBody
	public BaseVO sendPhoneLoginCode(HttpServletRequest request){
		ActionLogCache.insert(request, "发送手机号登录的验证码");
		return smsLogService.sendPhoneLoginCode(request);
	}
	
}
