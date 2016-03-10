package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.RolePermission;
/**
 * 权限相关的角色对应哪些资源
 * @author 管雷鸣
 *
 */
public interface RolePermissionService {

	public void save(RolePermission transientInstance);

	public void delete(RolePermission persistentInstance);

	public RolePermission findById(java.lang.Integer id);

	public List findByProperty(String propertyName, Object value);

	public List<RolePermission> findByRoleId(Object roleId);

	public List<RolePermission> findByPermissionId(Object permissionId);

	public List<RolePermission> findByExample(RolePermission instance);

	public List findAll();
	
	public List<Permission> findPermissionByRoleId(Integer roleId);
}