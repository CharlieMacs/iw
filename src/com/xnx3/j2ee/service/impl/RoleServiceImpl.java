package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.RoleDAO;
import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDao;
	
	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void save(Role transientInstance) {
		// TODO Auto-generated method stub
		roleDao.save(transientInstance);
	}

	@Override
	public void delete(Role persistentInstance) {
		// TODO Auto-generated method stub
		roleDao.delete(persistentInstance);
	}

	@Override
	public Role findById(Integer id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return roleDao.findByProperty(propertyName, value);
	}

	@Override
	public List<Role> findByExample(Role instance) {
		// TODO Auto-generated method stub
		return roleDao.findByExample(instance);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}

}
