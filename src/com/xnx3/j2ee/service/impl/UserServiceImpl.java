package com.xnx3.j2ee.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.SmsLogDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.dao.UserRoleDAO;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.UploadFileVO;
import com.xnx3.j2ee.entity.*;
import com.xnx3.media.ImageUtil;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.ossbean.PutResult;

public class UserServiceImpl implements UserService{
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);  

	private UserDAO userDao;
	private LogDAO logDao;
	private UserRoleDAO userRoleDAO;
	private SmsLogDAO smsLogDAO;
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public LogDAO getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDAO logDao) {
		this.logDao = logDao;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public SmsLogDAO getSmsLogDAO() {
		return smsLogDAO;
	}

	public void setSmsLogDAO(SmsLogDAO smsLogDAO) {
		this.smsLogDAO = smsLogDAO;
	}

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
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserOrEmailNotNull"));
			return baseVO;
		}
		if(password==null || password.length() == 0){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginPasswordNotNull"));
			return baseVO;
		}
		
		List<User> l = null;
		if(username.indexOf("@")>-1){		//判断是用户名还是邮箱登陆的
			l = findByEmail(username);
		}else{
			l = findByUsername(username);
		}
		if(l!=null && l.size()>0){
			User user = l.get(0);
			
			String md5Password = new Md5Hash(password, user.getSalt(),Global.USER_PASSWORD_SALT_NUMBER).toString();
			//检验密码是否正确
			if(md5Password.equals(user.getPassword())){
				//检验此用户状态是否正常，是否被冻结
				if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
					baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserFreeze"));
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
				baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_loginSuccess"));
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginPasswordFailure"));
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserNotFind"));
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
		
		//判断用户名、邮箱、手机号是否有其中为空的
		if(user.getUsername()==null||user.getUsername().equals("")||user.getEmail()==null||user.getEmail().equals("")||user.getPassword()==null||user.getPassword().equals("")){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regDataNotAll"));
		}
		
		//判断用户名、邮箱、手机号是否有其中已经注册了，唯一性
		if(findByEmail(user.getEmail()).size() > 0){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regFailureForEmailAlreadyExist"));
			return baseVO;
		}
		
		//判断用户名唯一性
		if(findByUsername(user.getUsername()).size() > 0){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regFailureForUsernameAlreadyExist"));
			return baseVO;
		}
		
		//判断手机号唯一性
		if(user.getPhone() != null && user.getPhone().length() > 0){
			if(findByPhone(user.getUsername()) != null){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regFailureForPhoneAlreadyExist"));
				return baseVO;
			}
		}
		
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
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regDataNotAll"));
		}else{
			if(user.getUsername().length() > 20){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_userNameToLong"));
			}
			
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
				baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_regSuccess"));
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_regFailure"));
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
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodePhoneFailure"));
			return baseVO;
		}
		if(code==null || code.length() != 6){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodeCodeFailure"));
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
    			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodeRegFailure"));
    			return baseVO;
    		}
    		
    		//检验此用户状态是否正常，是否被冻结
			if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserFreeze"));
				return baseVO;
			}
    		
    		/****更改SmsLog状态*****/
    		smsLog.setUserid(user.getId());
    		smsLog.setUsed(SmsLog.USED_TRUE);
    		smsLogDAO.save(smsLog);
    		
    		/*******更改User状态******/
    		user.setLasttime(DateUtil.timeForUnix10());
    		user.setLastip(IpUtil.getIpAddress(request));
			save(user);
    		
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
			baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_loginSuccess"));
			return baseVO;
    	}else{
    		baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodeCodeNotFind"));
    		return baseVO;
    	}
	}
	

	/**
	 * 手机号登陆，会自动检测上次登陆的ip，若上次登陆的ip跟当前的ip一样，则这个手机用户登陆成功
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交两个参数：phone(手机号)、code(手机收到的动态验证码)
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO loginByPhone(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String phone = request.getParameter("phone");
		if(phone==null){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhonePhoneFailure"));
			return baseVO;
		}else{
			phone = phone.replaceAll(" ", "");
			if(phone.length() != 11){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhonePhoneFailure"));
				return baseVO;
			}
		}
		
    	logger.debug("phone:"+phone);
		User user = findByPhone(phone);
		logger.debug("根据用户手机号查询得到用户得信息,user:"+user);
		if(user == null){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneUserNotFind"));
			return baseVO;
		}
		
		//ip检测
		String ip = IpUtil.getIpAddress(request);
		logger.debug("得到用户请求得IP地址："+ip);
		if(!(user.getLastip().equals(ip) || user.getRegip().equals(ip))){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneIpFailure"));
			return baseVO;
		}
		
		//检验此用户状态是否正常，是否被冻结
		if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserFreeze"));
			return baseVO;
		}
		logger.debug("检验此用户状态是否正常，是否被冻结，未冻结，正常");
		
		/*******更改User状态******/
		user.setLasttime(DateUtil.timeForUnix10());
		user.setLastip(IpUtil.getIpAddress(request));
		save(user);
		logger.debug("更新User状态，更新后的User为："+user);
		
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
		baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_loginSuccess"));
		return baseVO;
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
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateNicknameNicknameIsNull"));
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
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_freezeUserIsNotFind"));
			}else{
				user.setIsfreeze(User.ISFREEZE_FREEZE);
				save(user);
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_freezeUserPleaseEntryId"));
		}
		
		return baseVO;
	}

	@Override
	public BaseVO unfreezeUser(int id) {
		BaseVO baseVO = new BaseVO();
		if(id > 0){
			User user = findById(id);
			if(user == null){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_unfreezeUserIsNotFind"));
			}else{
				user.setIsfreeze(User.ISFREEZE_NORMAL);
				save(user);
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_unfreezeUserPleaseEntryId"));
		}
		return baseVO;
	}

	@Override
	public UploadFileVO updateHeadByOSS(MultipartFile head) {
		UploadFileVO uploadFileVO = new UploadFileVO();
		if(head == null || head.isEmpty()){
			uploadFileVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_uploadHeadImageNotFind"));
			return uploadFileVO;
		}
		
		User user = ShiroFunc.getUser();
		String fileSuffix = "png";
		fileSuffix = Lang.findFileSuffix(head.getOriginalFilename());
		String newHead = user.getId()+"."+fileSuffix;
		try {
			PutResult result = OSSUtil.put("image/head/"+newHead, head.getInputStream());
			uploadFileVO.setFileName(result.getFileName());
			uploadFileVO.setPath(result.getPath());
			uploadFileVO.setUrl(result.getUrl());
		} catch (IOException e) {
			e.printStackTrace();
			uploadFileVO.setBaseVO(BaseVO.FAILURE, e.getMessage());
			return uploadFileVO;
		}
		
		if(!(user.getHead().equals(newHead))){
			User u = findById(user.getId());
			u.setHead(newHead);
			save(u);
			ShiroFunc.getUser().setHead(newHead);
		}
		logDao.insert("USER_UPDATEHEAD");
		
		return uploadFileVO;
	}

	@Override
	public BaseVO updateSex(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String sex = request.getParameter("sex");
		if(sex == null || sex.length()<0){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateSexSexNotIsNull"));
			return baseVO;
		}
		if(!(sex.equals("男") || sex.equals("女"))){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateSexEntryFailure"));
			return baseVO;
		}
		User u = findById(ShiroFunc.getUser().getId());
		u.setSex(sex);
		save(u);
		logDao.insert("USER_UPDATE_SEX", ShiroFunc.getUser().getSex()+"修改为"+sex);
		ShiroFunc.getUser().setSex(sex);
		
		return baseVO; 
	}

	@Override
	public BaseVO updateNickname(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String nickname = request.getParameter("nickname");
		if(nickname == null){
			nickname = "";
		}
		nickname = StringUtil.filterHtmlTag(nickname);
		if(nickname.length()==0){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateNicknameNotNull"));
			return baseVO;
		}
		if(nickname.length()>15){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateNicknameSizeFailure"));
			return baseVO;
		}
		
		User u = findById(ShiroFunc.getUser().getId());
		u.setNickname(nickname);
		save(u);
		logDao.insert("USER_UPDATE_NICKNAME", ShiroFunc.getUser().getNickname());
		ShiroFunc.getUser().setNickname(nickname);
		
		return baseVO;
	}

	@Override
	public BaseVO updateSign(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String sign = request.getParameter("sign");
		if(sign == null){
			sign = "";
		}
		sign = StringUtil.filterHtmlTag(sign);
		if(sign.length()>40){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_updateSignSizeFailure"));
			return baseVO;
		}
		
		User u = findById(ShiroFunc.getUser().getId());
		u.setSign(sign);
		save(u);
		logDao.insert("USER_UPDATE_NICKNAME", sign);
		ShiroFunc.getUser().setSign(sign);
		
		return baseVO;
	}

	@Override
	public UploadFileVO updateHeadByOSS(HttpServletRequest request,String formFileName) {
		return updateHeadByOSS(request, formFileName, 0);
	}

	@Override
	public UploadFileVO updateHeadByOSS(HttpServletRequest request,
			String formFileName, int maxWidth) {
		UploadFileVO uploadFileVO = new UploadFileVO();
		MultipartFile multipartFile = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			List<MultipartFile> imageList = multipartRequest.getFiles(formFileName);  
			if(imageList.size() == 0){
				logger.debug("上传头像时，未发现头像 ------"+Global.getLanguage("user_uploadHeadImageNotFind"));
				uploadFileVO.setResult(UploadFileVO.NOTFILE);
				uploadFileVO.setInfo(Global.getLanguage("user_uploadHeadImageNotFind"));
				return uploadFileVO;
			}else{
				logger.debug("上传头像，已发现头像的multipartFile");
				multipartFile = imageList.get(0);
			}
	    }
		
		if(multipartFile == null || multipartFile.isEmpty()){
			uploadFileVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_uploadHeadImageNotFind"));
			logger.debug("上传头像的multipartFile为空，不存在上传的头像 ------"+Global.getLanguage("user_uploadHeadImageNotFind"));
			return uploadFileVO;
		}
		
		User user = ShiroFunc.getUser();
		String fileSuffix = "png";
		fileSuffix = Lang.findFileSuffix(multipartFile.getOriginalFilename());
		logger.debug("上传头像，获得上传的图片的后缀名："+fileSuffix);
		String newHead = user.getId()+"."+fileSuffix;
		try {
			InputStream is = null;
			if(maxWidth == 0){
				logger.debug("上传头像，没有开启头像缩放功能");
				is = multipartFile.getInputStream();
			}else{
				logger.debug("上传头像，开启了头像缩放功能，限制最大宽度:"+maxWidth);
				is = ImageUtil.proportionZoom(multipartFile.getInputStream(), maxWidth, fileSuffix);
			}
			PutResult result = OSSUtil.put("image/head/"+newHead, is);
			logger.info("头像上传,阿里云返回值:"+result);
			uploadFileVO.setFileName(result.getFileName());
			uploadFileVO.setPath(result.getPath());
			uploadFileVO.setUrl(result.getUrl());
			uploadFileVO.setBaseVO(UploadFileVO.SUCCESS, "上传成功");
			logger.debug("头像上传执行成功，结果："+uploadFileVO);
		} catch (IOException e) {
			e.printStackTrace();
			uploadFileVO.setBaseVO(BaseVO.FAILURE, e.getMessage());
			return uploadFileVO;
		}
		
		if(!(user.getHead().equals(newHead))){
			User u = findById(user.getId());
			u.setHead(newHead);
			save(u);
			ShiroFunc.getUser().setHead(newHead);
		}
		logDao.insert("USER_UPDATEHEAD");
		logger.debug("头像上传函数的返回结果："+uploadFileVO);
		return uploadFileVO;
	}

	@Override
	public BaseVO loginByUserid(HttpServletRequest request, int userid) {
		BaseVO baseVO = new BaseVO();
//		if(userid == 0){
		//请传入要登陆用户的id
//			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhonePhoneFailure"));
//			return baseVO;
//		}
		
		User user = findById(userid);
		if(user == null){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneUserNotFind"));
			return baseVO;
		}
		
		//ip检测
		String ip = IpUtil.getIpAddress(request);
		if(!(user.getLastip().equals(ip) || user.getRegip().equals(ip))){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneIpFailure"));
			return baseVO;
		}
		
		//检验此用户状态是否正常，是否被冻结
		if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserFreeze"));
			return baseVO;
		}
		
		/*******更改User状态******/
		user.setLasttime(DateUtil.timeForUnix10());
		user.setLastip(IpUtil.getIpAddress(request));
		save(user);
		
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
		baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_loginSuccess"));
		return baseVO;
	}

	@Override
	public BaseVO loginForUserId(HttpServletRequest request, int userId) {
		BaseVO baseVO = new BaseVO();
		User user = findById(userId);
		if(user == null){
			logger.debug("用户不存在");
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneUserNotFind"));
			return baseVO;
		}
		
		//检验此用户状态是否正常，是否被冻结
		if(user.getIsfreeze() == User.ISFREEZE_FREEZE){
			logger.debug("此用户被冻结，无法设置为登陆用户");
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginUserFreeze"));
			return baseVO;
		}
		
		/*******更改User状态******/
		user.setLasttime(DateUtil.timeForUnix10());
		user.setLastip(IpUtil.getIpAddress(request));
		save(user);
		logger.debug("设置指定userId为登陆用户，设置后得User："+user);
		
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
		baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("user_loginSuccess"));
		return baseVO;
	}

}
