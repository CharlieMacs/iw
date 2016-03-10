package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.entity.*;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDAO userDao;
	
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

	
	
}
