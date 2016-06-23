package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 用户
 * @author 管雷鸣
 *
 */
public interface UserService {

	public void save(User transientInstance);

	public void delete(User persistentInstance);

	public User findById(java.lang.Integer id);

	public List<User> findByExample(User instance);

	public List findByProperty(String propertyName, Object value);

	public List<User> findByUsername(Object username);

	public List<User> findByEmail(Object email);

	public List<User> findByPassword(Object password);

	public List<User> findByHead(Object head);

	public List<User> findByNickname(Object nickname);

	public List<User> findByRegtime(Object regtime);

	public List<User> findByLasttime(Object lasttime);

	public List<User> findByRegip(Object regip);

	public List<User> findByLastip(Object lastip);
	
	/**
	 * 根据手机号取用户信息。若手机号不存在，返回null
	 * @param phone
	 * @return
	 */
	public User findByPhone(Object phone);

	public List findAll();
	
	public int findRecordNumber();

	public User merge(User detachedInstance);

	public void attachDirty(User instance);

	public void attachClean(User instance);

	public void findByReferrerid(Object referrerid);
	
	/**
	 * 用户名＋密码进行登陆
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交两个参数：username(用户名/邮箱)、password(密码)
	 * @return {@link BaseVO}
	 */
	public BaseVO loginByUsernameAndPassword(HttpServletRequest request);
	
	/**
	 * 手机号＋动态验证码登陆
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交两个参数：phone(手机号)、code(手机收到的动态验证码)
	 * @return {@link BaseVO}
	 */
	public BaseVO loginByPhoneAndCode(HttpServletRequest request);
	
	/**
	 * 手机号登陆，会自动检测上次登陆的ip，若上次登陆的ip跟当前的ip一样，则这个手机用户登陆成功
	 * @param request {@link HttpServletRequest} 
	 * 		<br/>登陆时form表单需提交一个参数：phone(手机号)
	 * @return {@link BaseVO}
	 */
	public BaseVO loginByPhone(HttpServletRequest request);
	
	/**
	 * 注册页面初始化数据，注册页面填写表单时先调用此，初始化推荐人相关数据
	 * <br/>GET/POST参数：inviteid 推荐人id，user.id。可为空为不传
	 */
	public void regInit(HttpServletRequest request);
	
	/**
	 * 注册
	 * @param user {@link User} 
	 * 		<br/>表单的用户名(username)、 密码(password)为必填项
	 * @param request {@link HttpServletRequest}
	 * @return {@link BaseVO}
	 */
	public BaseVO reg(User user ,HttpServletRequest request);
	
	/**
	 * 当前已登录的用户退出登录，注销
	 */
	public void logout();
	
	/**
	 * 修改昵称
	 * @param request {@link HttpServletRequest} 
	 * 			<br/>form表单需提交参数：nickname(要修改成的昵称)
	 * @return {@link BaseVO}
	 */
	public BaseVO updateNickName(HttpServletRequest request);
	
	/**
	 * 冻结用户
	 * @param id 用户id，user.id
	 * @return {@link BaseVO}
	 */
	public BaseVO freezeUser(int id);
	
	/**
	 * 解除冻结用户
	 * @param id 用户id，user.id
	 * @return {@link BaseVO}
	 */
	public BaseVO unfreezeUser(int id);
	
	/**
	 * 利用OSS上传头像
	 * @param head
	 * @return
	 */
	public BaseVO updateHeadByOSS(MultipartFile head);
	
	/**
	 * 修改性别
	 * @param request GET／POST传入如： sex=男
	 */
	public BaseVO updateSex(HttpServletRequest request);
	
	/**
	 * 修改昵称
	 * @param request GET／POST传入如： nickname=管雷鸣  不允许为空。字符限制1～15个汉字或英文
	 */
	public BaseVO updateNickname(HttpServletRequest request);
	
	/**
	 * 修改签名
	 * @param request GET／POST传入如： sign=我是签名  允许为空。字符限制0～40个汉字或英文
	 */
	public BaseVO updateSign(HttpServletRequest request);
}