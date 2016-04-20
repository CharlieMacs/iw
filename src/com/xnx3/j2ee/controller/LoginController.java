package com.xnx3.j2ee.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.bean.PermissionTree;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.entity.UserRole;
import com.xnx3.j2ee.service.LogService;
import com.xnx3.j2ee.service.SmsLogService;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.shiro.ActiveUser;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.SendPhoneMsgUtil;

/**
 * 登录、注册、退出
 * @author 管雷鸣
 *
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
	private static int codeValidity = 0;	//
	public static int everyDayPhoneNum = 0;	//短信同一手机号，某个功能每天发送的条数限制，超过这个条数，这个功能便无法再次发送短信了。
	public static int everyDayIpNum = 0;	//同上，只不过这个是针对ip。介于一个wifi有很多终端，都是同一个ip，这个数值可能比较大
	
	@Resource
	private UserService userService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private LogService logService;
	@Resource
	private SmsLogService smsLogService;
	
	static{
		everyDayPhoneNum = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.everyDayPhoneNum"), 0);
		everyDayIpNum = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.everyDayIpNum"), 0);
		codeValidity = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("sms.codeValidity"), 0);
	}
	
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
		user.setAuthority(Global.system.get("USER_REG_ROLE"));
		user.setCurrency(0);
		user.setReferrerid(0);
		user.setFreezecurrency(0);
		user.setHead("default.png");
		
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
				userRole.setRoleid(Lang.stringToInt(Global.system.get("USER_REG_ROLE"), 0));
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
			log.setType(Log.typeMap.get("USER_INVITEREG_AWARD"));
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
		if(getUser() != null){
			return redirect("user/info.do");
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
	

	/**
	 * 登陆请求验证
	 * @param phone 登录的手机号
	 * @param code 发送到手机的验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("loginSubmit")
	public String loginSubmit(
			@RequestParam(value = "username", required = false,defaultValue="") String username,
			@RequestParam(value = "password", required = false,defaultValue="") String password,
			HttpServletRequest request , HttpServletResponse response,Model model) throws Exception{
		if(username.length() == 0 ){
			return error(model, "用户名/ID/邮箱不能为空");
		}
		if(password.length() == 0){
			return error(model, "密码不能为空");
		}
		
		List<User> l = userService.findByUsername(username);
		if(l!=null){
			User user = l.get(0);
			
			String md5Password = new Md5Hash(password, user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
			if(md5Password.equals(user.getPassword())){
				user.setLasttime(DateUtil.timeForUnix10());
				userService.save(user);
				
				Log log = new Log();
				log.setAddtime(new Date());
				log.setType(Log.typeMap.get("USER_LOGIN_SUCCESS"));
				log.setUserid(user.getId());
				logService.save(log);
				
				UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getUsername());
		        token.setRememberMe(true);
				Subject currentUser = SecurityUtils.getSubject();  
				
				try {  
					currentUser.login(token);  
				} catch ( UnknownAccountException uae ) {
				} catch ( IncorrectCredentialsException ice ) {
				} catch ( LockedAccountException lae ) {
				} catch ( ExcessiveAttemptsException eae ) {
				} catch ( org.apache.shiro.authc.AuthenticationException ae ) {  
				}
				
				return success(model, "登陆成功","user/info.do");
			}else{
				return error(model, "密码错误！");
			}
			
		}else{
			return error(model, "用户不存在");
		}
	}

	/**
	 * 登陆请求验证
	 * @param user user.getUsername() 包含用户名/邮箱
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("phoneVerifyLogin")
	public String phoneVerifyLogin(User user,HttpServletRequest request , HttpServletResponse response,Model model) throws Exception{
		if(getUser() != null){
			return redirect("user/info.do");
		}
		
		return "login/phoneVerifyLogin";
	}
	
	
	/**
	 * 登陆请求验证
	 * @param phone 登录的手机号
	 * @param code 发送到手机的验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("phoneVerifyLoginSubmit")
	@ResponseBody
	public BaseVO phoneVerifyLoginSubmit(
			@RequestParam(value = "phone", required = false,defaultValue="") String phone,
			@RequestParam(value = "code", required = false,defaultValue="") String code,
			HttpServletRequest request , HttpServletResponse response,Model model) throws Exception{
		BaseVO baseVO = new BaseVO();
	
		if(phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入正确的手机号");
			return baseVO;
		}
		if(code.length() != 6){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入正确的验证码");
			return baseVO;
		}
		
    	List<SmsLog> smsLogList;
    	if(codeValidity == 0){
    		SmsLog smsLogSearch = new SmsLog();
        	smsLogSearch.setType(SmsLog.TYPE_LOGIN);
        	smsLogSearch.setUsed(SmsLog.USED_FALSE);
        	smsLogSearch.setCode(code);
    		smsLogList = smsLogService.findByExample(smsLogSearch);
    	}else{
    		int currentTime = DateUtil.timeForUnix10();
    		smsLogList = smsLogService.findByPhoneAddtimeUsedType(phone, currentTime-codeValidity, SmsLog.USED_FALSE, SmsLog.TYPE_LOGIN,code);
    	}
    	if(smsLogList.size()>0){
    		SmsLog smsLog = smsLogList.get(0);
    		User user = userService.findByPhone(phone);
    		if(user == null){
    			//如果没有用户，则模拟注册一个
    			Random random = new Random();
    			user = new User();
    			String password = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
    			user.setUsername(phone);
    			user.setEmail(phone+"@139.com");
    			user.setPassword(password);
    			user.setPhone(phone);
    			String regSubmit = regSubmit(user, response, request, model);
    		}
    		user = userService.findByPhone(phone);
    		if(user == null){
    			baseVO.setBaseVO(BaseVO.FAILURE, "自动注册用户失败！");
    			return baseVO;
    		}
    		
    		/****更改SmsLog状态*****/
    		smsLog.setUserid(user.getId());
    		smsLog.setUsed(SmsLog.USED_TRUE);
    		smsLogService.save(smsLog);
    		
    		/*******记录登录日志******/
    		Log log = new Log();
			log.setAddtime(new Date());
			log.setType(Log.typeMap.get("USER_LOGIN_SUCCESS"));
			log.setUserid(user.getId());
			logService.save(log);
			
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getUsername());
	        token.setRememberMe(true);
			Subject currentUser = SecurityUtils.getSubject();  
			
			try {  
				currentUser.login(token);  
			} catch ( UnknownAccountException uae ) {
			} catch ( IncorrectCredentialsException ice ) {
			} catch ( LockedAccountException lae ) {
			} catch ( ExcessiveAttemptsException eae ) {
			} catch ( org.apache.shiro.authc.AuthenticationException ae ) {  
			}
			
			baseVO.setBaseVO(BaseVO.SUCCESS, "登录成功");
			return baseVO;
    	}else{
    		baseVO.setBaseVO(BaseVO.FAILURE, "验证码不存在！");
    		return baseVO;
    	}
	}
	
	/**
	 * 点击按钮发送验证码，就会触发此方法
	 * @param phone 限制11位
	 * @return {@link BaseVO}
	 */
	@RequestMapping("sendLoginVerify")
	@ResponseBody
	public BaseVO sendLoginVerify(@RequestParam(value = "phone", required = false, defaultValue="") String phone,HttpServletRequest request){
		BaseVO baseVO = new BaseVO();
		if(phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入正确的手机号");
		}else{
			//此参数决定最终是否可发送短信验证码
			boolean send = false;
			
			//查询当前手机号是否已发送满条件
			if(everyDayPhoneNum > 0){
				int phoneNum = smsLogService.findByPhoneNum(phone, SmsLog.TYPE_LOGIN);
				if(phoneNum<everyDayPhoneNum){
					send = true;
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, "此手机当天验证码发送已达上限！请明日再进行尝试");
					send = false;
				}
			}else{
				send = true;
			}
			
			if(send){
				if(everyDayIpNum > 0){
					int ipNum = smsLogService.findByIpNum(IpUtil.getIpAddress(request), SmsLog.TYPE_LOGIN);
					if(ipNum<everyDayIpNum){
						send = true;
					}else{
						baseVO.setBaseVO(BaseVO.FAILURE, "此IP当天验证码发送已达上限！请明日再进行尝试");
						send = false;
					}
				}
			}
			
			//发送验证码流程逻辑
			if(send){
				Random random = new Random();
				String code = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
				
				SmsLog smsLog = new SmsLog();
				smsLog.setAddtime(DateUtil.timeForUnix10());
				smsLog.setCode(code);
				smsLog.setIp(IpUtil.getIpAddress(request));
				smsLog.setPhone(phone);
				smsLog.setType(SmsLog.TYPE_LOGIN);
				smsLog.setUsed(SmsLog.USED_FALSE);
				smsLog.setUserid(0);
				smsLogService.save(smsLog);
				
				if(smsLog.getId()>0){
					SendPhoneMsgUtil.send(phone, code);
					baseVO.setBaseVO(BaseVO.SUCCESS, "验证码已发送至您手机");
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, "验证码保存失败！");
				}
			}
		}
		
		return baseVO;
	}
	
	
	
}
