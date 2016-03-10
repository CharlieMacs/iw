package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.System;
/**
 * 系统表
 * @author 管雷鸣
 *
 */
public interface SystemService {

	public void save(System transientInstance);

	public void delete(System persistentInstance);

	public System findById(java.lang.Integer id);

	public List findByProperty(String propertyName, Object value);

	public List<System> findByExample(System instance);

	public List<System> findByName(Object name);

	public List<System> findByListshow(Object listshow);
	
	public List findAll();

}