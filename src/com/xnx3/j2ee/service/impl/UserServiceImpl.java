package com.xnx3.j2ee.service.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.SmsLogDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.dao.UserRoleDAO;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.entity.*;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDAO userDao;
	@Resource
	private LogDAO logDao;
	@Resource
	private UserRoleDAO userRoleDAO;
	@Resource
	private SmsLogDAO smsLogDAO;
	
	
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void delete(User persistentInstance) {
		// TODO Auto-generated method stub
		userDao.delete(persistentInstance);
		
	}

	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.findById(id);
	}

	@Override
	public List<User> findByExample(User instance) {
		// TODO Auto-generated method stub
		return userDao.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return userDao.findByProperty(propertyName, value);
	}

	@Override
	public List<User> findByUsername(Object username) {
		// TODO Auto-generated method stub
		return userDao.findByUsername(username);
	}

	@Override
	public List<User> findByEmail(Object email) {
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}

	@Override
	public List<User> findByPassword(Object password) {
		// TODO Auto-generated method stub
		return userDao.findByPassword(password);
	}

	@Override
	public List<User> findByHead(Object head) {
		// TODO Auto-generated method stub
		return userDao.findByHead(head);
	}

	@Override
	public List<User> findByNickname(Object nickname) {
		// TODO Auto-generated method stub
		return userDao.findByNickname(nickname);
	}

	@Override
	public List<User> findByRegtime(Object regtime) {
		// TODO Auto-generated method stub
		return userDao.findByRegtime(regtime);
	}

	@Override
	public List<User> findByLasttime(Object lasttime) {
		// TODO Auto-generated method stub
		return userDao.findByLasttime(lasttime);
	}

	@Override
	public List<User> findByRegip(Object regip) {
		// TODO Auto-generated method stub
		return userDao.findByRegip(regip);
	}

	@Override
	public List<User> findByLastip(Object lastip) {
		// TODO Auto-generated method stub
		return userDao.findByLastip(lastip);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public User merge(User detachedInstance) {
		// TODO Auto-generated method stub
		return userDao.merge(detachedInstance);
	}

	@Override
	public void attachDirty(User instance) {
		// TODO Auto-generated method stub
		userDao.attachDirty(instance);
	}

	@Override
	public void attachClean(User instance) {
		// TODO Auto-generated method stub
		userDao.attachClean(instance);
	}

	@Override
	public int findRecordNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void findByReferrerid(Object referrerid) {
		// TODO Auto-generated method stub
		userDao.findByReferrerid(referrerid);
	}
	
	@Override
	public User findByPhone(Object phone){
		List<User> list = userDao.findByPhone(phone);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 登陆
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交两个参数：username(用户名/邮箱)、password(密码)
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO loginByUsernameAndPassword(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username==null || username.length() == 0 ){
			baseVO.setBaseVO(BaseVO.FAILURE, "用户名/邮箱不能为空");
			return baseVO;
		}
		if(password==null || password.length() == 0){
			baseVO.setBaseVO(BaseVO.FAILURE, "密码不能为空");
			return baseVO;
		}
		
		List<User> l = null;
		if(username.indexOf("@")>-1){		//判断是用户名还是邮箱登陆的
			l = findByEmail(username);
		}else{
			l = findByUsername(username);
		}
		if(l!=null){
			User user = l.get(0);
			
			String md5Password = new Md5Hash(password, user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
			//检验密码是否正确
			if(md5Password.equals(user.getPassword())){
				//检验此用户状态是否正常，是否被冻结
				if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
					baseVO.setBaseVO(BaseVO.FAILURE, "您的账号已被冻结！无法登陆");
					return baseVO;
				}
				
				user.setLasttime(DateUtil.timeForUnix10());
				user.setLastip(IpUtil.getIpAddress(request));
				save(user);
				
				UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getUsername());
		        token.setRememberMe(false);
				Subject currentUser = SecurityUtils.getSubject();  
				
				try {  
					currentUser.login(token);  
				} catch ( UnknownAccountException uae ) {
					java.lang.System.out.println("UnknownAccountException:"+uae.getMessage());
				} catch ( IncorrectCredentialsException ice ) {
					java.lang.System.out.println("IncorrectCredentialsException:"+ice.getMessage());
				} catch ( LockedAccountException lae ) {
					java.lang.System.out.println("LockedAccountException:"+lae.getMessage());
				} catch ( ExcessiveAttemptsException eae ) {
					java.lang.System.out.println("ExcessiveAttemptsException:"+eae.getMessage());
				} catch ( org.apache.shiro.authc.AuthenticationException ae ) {  
					java.lang.System.out.println("AuthenticationException:"+ae.getMessage());
				}
				logDao.insert("USER_LOGIN_SUCCESS");
				baseVO.setBaseVO(BaseVO.SUCCESS, "登陆成功");
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, "密码错误！");
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "用户不存在");
		}
		
		return baseVO;
	}
	
	/**
	 * 注册
	 * @param user {@link User} 
	 * 		<br/>表单的用户名(username)、 密码(password)为必填项
	 * @param request {@link HttpServletRequest}
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO reg(User user, HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		user.setRegip(IpUtil.getIpAddress(request));
		user.setLastip(IpUtil.getIpAddress(request));
		user.setRegtime(DateUtil.timeForUnix10());
		user.setLasttime(DateUtil.timeForUnix10());
		user.setNickname(user.getUsername());
		user.setAuthority(Global.system.get("USER_REG_ROLE"));
		user.setCurrency(0);
		user.setReferrerid(0);
		user.setFreezemoney(0F);
		user.setMoney(0F);
		user.setIsfreeze(User.ISFREEZE_NORMAL);
		user.setHead("default.png");
		user.setIdcardauth(User.IDCARDAUTH_NO);
		
		String inviteid = null;
		if(request.getSession().getAttribute("inviteid")!=null){
			inviteid = request.getSession().getAttribute("inviteid").toString();
		}
		
		User referrerUser1 = null;
		if(inviteid!=null&&inviteid.length()>0){
			int referrerid = Lang.stringToInt(inviteid, 0);
			referrerUser1 = findById(referrerid);		//一级下线
			if(referrerUser1!=null){
				user.setReferrerid(referrerid);
			}
		}
		
		if(user.getUsername()==null||user.getUsername().equals("")||user.getEmail()==null||user.getEmail().equals("")||user.getPassword()==null||user.getPassword().equals("")){
			baseVO.setBaseVO(BaseVO.FAILURE, "信息不全！请重新输入");
		}else{
			Random random = new Random();
			user.setSalt(random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+"");
	        String md5Password = new Md5Hash(user.getPassword(), user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
			user.setPassword(md5Password);
			
			save(user);
			if(user.getId()>0){
				//已注册成功，自动登录成用户
				UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getUsername());
		        token.setRememberMe(false);
				Subject currentUser = SecurityUtils.getSubject();  
				
				try {  
					currentUser.login(token);  
				} catch ( UnknownAccountException uae ) {
				} catch ( IncorrectCredentialsException ice ) {
				} catch ( LockedAccountException lae ) {
				} catch ( ExcessiveAttemptsException eae ) {
				} catch ( org.apache.shiro.authc.AuthenticationException ae ) {  
				}
				
				//赋予该用户系统设置的默认角色
				UserRole userRole = new UserRole();
				userRole.setRoleid(Lang.stringToInt(Global.system.get("USER_REG_ROLE"), 0));
				userRole.setUserid(user.getId());
				userRoleDAO.save(userRole);
				
				//推荐人增加奖励
				if(user.getReferrerid()>0){	//是否有直接推荐人
					referrerAddAward(referrerUser1, Global.system.get("INVITEREG_AWARD_ONE"), user);
					
					if(referrerUser1.getReferrerid()>0){	//一级下线有上级推荐人，拿到二级下线
						User referrerUser2 = findById(referrerUser1.getReferrerid());
						if(referrerUser2!=null){
							referrerAddAward(referrerUser2, Global.system.get("INVITEREG_AWARD_TWO"), user);
							
							if(referrerUser2.getReferrerid()>0){	//二级下线有上级推荐人，拿到三级下线
								User referrerUser3 = findById(referrerUser2.getReferrerid());
								if(referrerUser3!=null){
									referrerAddAward(referrerUser3, Global.system.get("INVITEREG_AWARD_THREE"), user);
									
									if(referrerUser3.getReferrerid()>0){	//三级下线有上级推荐人，拿到四级下线
										User referrerUser4 = findById(referrerUser3.getReferrerid());
										if(referrerUser4!=null){
											referrerAddAward(referrerUser4, Global.system.get("INVITEREG_AWARD_FOUR"), user);
										}
									}
								}
							}
						}
					}
				}
				
				logDao.insert("USER_REGISTER_SUCCESS");
				baseVO.setBaseVO(BaseVO.SUCCESS, "注册成功");
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, "注册失败");
			}
		}
		
		return baseVO;
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
			save(user);
			logDao.insert(regUser.getId(), "USER_INVITEREG_AWARD", addCurrency+"");
		}
	}
	
	/**
	 * 注册
	 * @param user {@link User} 
	 * 		<br/>表单的用户名(username)、 密码(password)为必填项
	 * @param request {@link HttpServletRequest}
	 * @return {@link BaseVO}
	 */
	@Override
	public void regInit(HttpServletRequest request) {
		String inviteid_ = request.getParameter("inviteid");
		if(inviteid_!=null&&inviteid_.length()>0){
			int inviteid = Lang.stringToInt(inviteid_, 0);
			
			User user = findById(inviteid);
			if(user!=null){
				request.getSession().setAttribute("inviteid", inviteid); 	//邀请人id
			}
		}
	}

	/**
	 * 手机号＋动态验证码登陆
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交两个参数：phone(手机号)、code(手机收到的动态验证码)
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO loginByPhoneAndCode(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		if(phone==null || phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入正确的手机号");
			return baseVO;
		}
		if(code==null || code.length() != 6){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入正确的验证码");
			return baseVO;
		}
		
    	List<SmsLog> smsLogList;
    	if(SmsLog.codeValidity == 0){
    		SmsLog smsLogSearch = new SmsLog();
        	smsLogSearch.setType(SmsLog.TYPE_LOGIN);
        	smsLogSearch.setUsed(SmsLog.USED_FALSE);
        	smsLogSearch.setCode(code);
    		smsLogList = smsLogDAO.findByExample(smsLogSearch);
    	}else{
    		int currentTime = DateUtil.timeForUnix10();
    		smsLogList = smsLogDAO.findByPhoneAddtimeUsedType(phone, currentTime-SmsLog.codeValidity, SmsLog.USED_FALSE, SmsLog.TYPE_LOGIN,code);
    	}
    	if(smsLogList.size()>0){
    		SmsLog smsLog = smsLogList.get(0);
    		User user = findByPhone(phone);
    		if(user == null){
    			//如果没有用户，则模拟注册一个
    			Random random = new Random();
    			user = new User();
    			String password = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
    			user.setUsername(phone);
    			user.setEmail(phone+"@139.com");
    			user.setPassword(password);
    			user.setPhone(phone);
    			reg(user, request);
    		}
    		user = findByPhone(phone);
    		if(user == null){
    			baseVO.setBaseVO(BaseVO.FAILURE, "自动注册用户失败！");
    			return baseVO;
    		}
    		
    		//检验此用户状态是否正常，是否被冻结
			if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
				baseVO.setBaseVO(BaseVO.FAILURE, "您的账号已被冻结！无法登陆");
				return baseVO;
			}
    		
    		/****更改SmsLog状态*****/
    		smsLog.setUserid(user.getId());
    		smsLog.setUsed(SmsLog.USED_TRUE);
    		smsLogDAO.save(smsLog);
			
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getUsername());
	        token.setRememberMe(false);
			Subject currentUser = SecurityUtils.getSubject();  
			
			try {  
				currentUser.login(token);  
			} catch ( UnknownAccountException uae ) {
			} catch ( IncorrectCredentialsException ice ) {
			} catch ( LockedAccountException lae ) {
			} catch ( ExcessiveAttemptsException eae ) {
			} catch ( org.apache.shiro.authc.AuthenticationException ae ) {  
			}
			
			logDao.insert("USER_LOGIN_SUCCESS");
			baseVO.setBaseVO(BaseVO.SUCCESS, "登录成功");
			return baseVO;
    	}else{
    		baseVO.setBaseVO(BaseVO.FAILURE, "验证码不存在！");
    		return baseVO;
    	}
	}

	@Override
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			logDao.insert("USER_LOGOUT");
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
	}

	/**
	 * 修改昵称
	 * @param request {@link HttpServletRequest} 
	 * 			<br/>form表单需提交参数：nickname(要修改成的昵称)
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO updateNickName(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String nickname = request.getParameter("nickname");
		if(nickname==null){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入要修改的昵称！");
		}else{
			User uu=findById(ShiroFunc.getUser().getId());
			String oldNickName = uu.getNickname();
			uu.setNickname(nickname);
			save(uu);
			
			//更新Session
			ShiroFunc.getCurrentActiveUser().setUser(uu);
	        
			logDao.insert("USER_UPDATE_NICKNAME", oldNickName);
		}
		return baseVO;
	}

	@Override
	public BaseVO freezeUser(int id) {
		BaseVO baseVO = new BaseVO();
		if(id > 0){
			User user = findById(id);
			if(user == null){
				baseVO.setBaseVO(BaseVO.FAILURE, "要冻结的用户不存在");
			}else{
				user.setIsfreeze(User.ISFREEZE_FREEZE);
				save(user);
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "请传入要冻结的用户id");
		}
		
		return baseVO;
	}

	@Override
	public BaseVO unfreezeUser(int id) {
		BaseVO baseVO = new BaseVO();
		if(id > 0){
			User user = findById(id);
			if(user == null){
				baseVO.setBaseVO(BaseVO.FAILURE, "要解除冻结的用户不存在");
			}else{
				user.setIsfreeze(User.ISFREEZE_NORMAL);
				save(user);
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "请传入要解除冻结的用户id");
		}
		return baseVO;
	}

}
