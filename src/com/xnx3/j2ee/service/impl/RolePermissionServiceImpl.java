package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.PermissionDAO;
import com.xnx3.j2ee.dao.RolePermissionDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.dao.UserRoleDAO;
import com.xnx3.j2ee.service.RolePermissionService;
import com.xnx3.j2ee.service.UserRoleService;
import com.xnx3.j2ee.entity.*;

@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService{

	@Resource
	private RolePermissionDAO rolePermissionDAO;

	@Override
	public void save(RolePermission transientInstance) {
		// TODO Auto-generated method stub
		rolePermissionDAO.save(transientInstance);
	}

	@Override
	public void delete(RolePermission persistentInstance) {
		// TODO Auto-generated method stub
		rolePermissionDAO.delete(persistentInstance);
	}

	@Override
	public RolePermission findById(Integer id) {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findById(id);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<RolePermission> findByRoleId(Object roleId) {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findByRoleId(roleId);
	}

	@Override
	public List<RolePermission> findByPermissionId(Object permissionId) {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findByPermissionId(permissionId);
	}

	@Override
	public List<RolePermission> findByExample(RolePermission instance) {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findByExample(instance);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return rolePermissionDAO.findAll();
	}

	public List<Permission> findPermissionByRoleId(Integer roleId){
		return rolePermissionDAO.findPermissionByRoleId(roleId);
	}
}
