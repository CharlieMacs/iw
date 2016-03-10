package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Permission;
/**
 * 权限相关的资源
 * @author 管雷鸣
 *
 */
public interface PermissionService {

	public void save(Permission transientInstance);

	public void delete(Permission persistentInstance);

	public Permission findById(java.lang.Integer id);

	public List findByProperty(String propertyName, Object value);

	public List<Permission> findByExample(Permission instance);

	public List findAll();

	/**
	 * 根据user.id查此用户拥有的permission
	 * @param userId 用户id
	 * @return
	 */
	public List<Permission> getPermissionByUserId(int userId);
}