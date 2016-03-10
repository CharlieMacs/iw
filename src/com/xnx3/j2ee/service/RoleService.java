package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Role;
/**
 * 权限相关的角色
 * @author 管雷鸣
 *
 */
public interface RoleService {

	public void save(Role transientInstance);

	public void delete(Role persistentInstance);

	public Role findById(java.lang.Integer id);

	public List findByProperty(String propertyName, Object value);

	public List<Role> findByExample(Role instance);

	public List findAll();

}