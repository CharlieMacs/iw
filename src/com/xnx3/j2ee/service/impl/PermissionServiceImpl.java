package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.PermissionDAO;
import com.xnx3.j2ee.service.PermissionService;
import com.xnx3.j2ee.entity.*;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService{
	
	@Resource
	private PermissionDAO permissionDAO;

	@Override
	public List<Permission> getPermissionByUserId(int userId) {
		// TODO Auto-generated method stub
		return permissionDAO.getPermissionByUserId(userId);
	}

	@Override
	public void save(Permission transientInstance) {
		// TODO Auto-generated method stub
		permissionDAO.save(transientInstance);
	}

	@Override
	public void delete(Permission persistentInstance) {
		// TODO Auto-generated method stub
		permissionDAO.delete(persistentInstance);
	}

	@Override
	public Permission findById(Integer id) {
		// TODO Auto-generated method stub
		return permissionDAO.findById(id);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return permissionDAO.findByProperty(propertyName, value);
	}


	@Override
	public List<Permission> findByExample(Permission instance) {
		// TODO Auto-generated method stub
		return permissionDAO.findByExample(instance);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return permissionDAO.findAll();
	}

}
