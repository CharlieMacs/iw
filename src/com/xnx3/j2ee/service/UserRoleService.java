package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.entity.UserRole;
/**
 * 权限相关的用户拥有哪个权限
 * @author 管雷鸣
 *
 */
public interface UserRoleService {

	public void save(UserRole transientInstance);

	public void delete(UserRole persistentInstance);

	public UserRole findById(java.lang.Integer id);

	public List findByProperty(String propertyName, Object value);

	public List<UserRole> findByExample(UserRole instance);

	/**
	 * 根据用户id，得到用户有哪些角色
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleByUserId(Integer userid);

	public List findAll();

	List<UserRole> findByUserId(Object userid);

}