package com.xnx3.j2ee.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.DBConfigure;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.entity.UserRole;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.DateUtil;
import com.xnx3.Lang;

/**
 * 登录、注册、退出
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
	
	@Resource
	private UserService userService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private LogService logService;
	
	/**
	 * 注册页面
	 */
	@RequestMapping("/reg")
	public String reg(HttpServletRequest request){
		String inviteid_ = request.getParameter("inviteid");
		if(inviteid_!=null&&inviteid_.length()>0){
			int inviteid = Lang.stringToInt(inviteid_, 0);
			
			User user = userService.findById(inviteid);
			if(user!=null){
				request.getSession().setAttribute("inviteid", inviteid); 	//邀请人id
			}
		}
		
		return "login/reg";
	}
	
	/**
	 * 注册相应请求地址
	 * @param user
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/regSubmit")
	public String regSubmit(User user ,HttpServletResponse response,HttpServletRequest request,Model model) throws IOException{
		String password = new String(user.getPassword());
		user.setRegip(request.getRemoteAddr());
		user.setRegtime(DateUtil.timeForUnix10());
		user.setLasttime(DateUtil.timeForUnix10());
		user.setNickname(user.getUsername());
		user.setAuthority(Global.system.get("USERREG_ROLE"));
		user.setCurrency(0);
		user.setReferrerid(0);
		
		String inviteid = null;
		if(request.getSession().getAttribute("inviteid")!=null){
			inviteid = request.getSession().getAttribute("inviteid").toString();
		}
		
		User referrerUser1 = null;
		if(inviteid!=null&&inviteid.length()>0){
			int referrerid = Lang.stringToInt(inviteid, 0);
			referrerUser1 = userService.findById(referrerid);		//一级下线
			if(referrerUser1!=null){
				user.setReferrerid(referrerid);
			}
		}
		
		JSONObject json=new JSONObject();
		if(user.getUsername()==null||user.getUsername().equals("")||user.getEmail()==null||user.getEmail().equals("")||user.getPassword()==null||user.getPassword().equals("")){
			return error(model, "信息不全！请重新输入");
		}else{
			Random random = new Random();
			user.setSalt(random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+"");
	        String md5Password = new Md5Hash(user.getPassword(), user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
			user.setPassword(md5Password);
			
			userService.save(user);
			if(user.getId()>0){
				//赋予该用户系统设置的默认角色
				UserRole userRole = new UserRole();
				userRole.setRoleid(Lang.stringToInt(Global.system.get("USERREG_ROLE"), 0));
				userRole.setUserid(user.getId());
				userRoleService.save(userRole);
				
				//推荐人增加奖励
				if(user.getReferrerid()>0){	//是否有直接推荐人
					referrerAddAward(referrerUser1, Global.system.get("INVITEREG_AWARD_ONE"), user);
					
					if(referrerUser1.getReferrerid()>0){	//一级下线有上级推荐人，拿到二级下线
						User referrerUser2 = userService.findById(referrerUser1.getReferrerid());
						if(referrerUser2!=null){
							referrerAddAward(referrerUser2, Global.system.get("INVITEREG_AWARD_TWO"), user);
							
							if(referrerUser2.getReferrerid()>0){	//二级下线有上级推荐人，拿到三级下线
								User referrerUser3 = userService.findById(referrerUser2.getReferrerid());
								if(referrerUser3!=null){
									referrerAddAward(referrerUser3, Global.system.get("INVITEREG_AWARD_THREE"), user);
									
									if(referrerUser3.getReferrerid()>0){	//三级下线有上级推荐人，拿到四级下线
										User referrerUser4 = userService.findById(referrerUser3.getReferrerid());
										if(referrerUser4!=null){
											referrerAddAward(referrerUser4, Global.system.get("INVITEREG_AWARD_FOUR"), user);
										}
									}
								}
							}
						}
					}
				}
				
				return "redirect:/login.do?username="+user.getUsername()+"&password="+password+"&rememberMe=true&autoLogin=true";
			}else{
				return error(model, "注册失败");
			}
		}
	}
	
	/**
	 * 注册成功后下线充值奖励
	 * @param user 要充值的下线user
	 * @param addCurrency_ 增加的货币值
	 * @param regUser 注册的用户的用户名
	 */
	private void referrerAddAward(User user,String addCurrency_,User regUser){
		int addCurrency = Lang.stringToInt(addCurrency_, 0);
		if(addCurrency>0){
			user.setCurrency(user.getCurrency()+addCurrency);
			userService.save(user);
			
			Log log = new Log();
			log.setAddtime(new Date());
			log.setUserid(user.getId());
			log.setValue(addCurrency+"");
			log.setGoalid(regUser.getId());
			log.setType(DBConfigure.LOG_USER_INVITEREG_AWARD);
			logService.save(log);
		}
	}
	
	/**
	 * 登陆请求验证
	 * @param user user.getUsername() 包含用户名/邮箱
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("login")
	public String login(User user,HttpServletRequest request , HttpServletResponse response,Model model) throws Exception{
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		//根据shiro返回的异常类路径判断，抛出指定异常信息
		if(exceptionClassName!=null){
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//最终会抛给异常处理器
				model.addAttribute("error", "账号不存在!");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				model.addAttribute("error", "用户名/密码错误!");
			} else if("randomCodeError".equals(exceptionClassName)){
				model.addAttribute("error", "验证码错误!");
			}else {
				model.addAttribute("error", "出现错误："+exceptionClassName);
			}
		}else{
			Subject subject= SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				//虽然没走这一步
				return "redirect:/user/info.do";
			}
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//可以通过get传入用户名密码自动实行登陆
		if(username!=null&&password!=null){
			model.addAttribute("username", username);
			model.addAttribute("password", password);
		};
		if(request.getParameter("autoLogin")!=null&&request.getParameter("autoLogin").equals("true")){
			model.addAttribute("autoLogin", "true");
		}
		
		return "login/login";
	}
	
}
