package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.User;

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
}