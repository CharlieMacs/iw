package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.PermissionDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.dao.UserRoleDAO;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.entity.*;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{

	@Resource
	private UserDAO userDao;
	
	@Resource
	private UserRoleDAO userRoleDao;
	
	@Resource
	private PermissionDAO permissionDAO;

	@Override
	public void save(UserRole transientInstance) {
		// TODO Auto-generated method stub
		userRoleDao.save(transientInstance);
	}

	@Override
	public void delete(UserRole persistentInstance) {
		// TODO Auto-generated method stub
		userRoleDao.delete(persistentInstance);
	}

	@Override
	public UserRole findById(Integer id) {
		// TODO Auto-generated method stub
		return userRoleDao.findById(id);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return userRoleDao.findByProperty(propertyName, value);
	}

	@Override
	public List<UserRole> findByExample(UserRole instance) {
		// TODO Auto-generated method stub
		return userRoleDao.findByExample(instance);
	}

	@Override
	public List<Role> findRoleByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return userRoleDao.findRoleByUserId(userid);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return userRoleDao.findAll();
	}
	
	@Override
	public List<UserRole> findByUserId(Object userid){
		return userRoleDao.findByUserId(userid);
	}
	
}
