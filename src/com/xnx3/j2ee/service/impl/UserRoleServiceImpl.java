package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.PermissionDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.dao.UserRoleDAO;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.entity.*;

public class UserRoleServiceImpl implements UserRoleService{

	private UserDAO userDao;
	private UserRoleDAO userRoleDao;
	private PermissionDAO permissionDAO;
	
	public UserDAO getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	public UserRoleDAO getUserRoleDao() {
		return userRoleDao;
	}
	public void setUserRoleDao(UserRoleDAO userRoleDao) {
		this.userRoleDao = userRoleDao;
	}
	public PermissionDAO getPermissionDAO() {
		return permissionDAO;
	}
	public void setPermissionDAO(PermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}

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
