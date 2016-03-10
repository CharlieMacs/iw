package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.RoleDAO;
import com.xnx3.j2ee.dao.SystemDAO;
import com.xnx3.j2ee.entity.System;
import com.xnx3.j2ee.service.SystemService;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	@Resource
	private SystemDAO systemDao;
	
	@Override
	public void save(System transientInstance) {
		// TODO Auto-generated method stub
		systemDao.save(transientInstance);
	}

	@Override
	public void delete(System persistentInstance) {
		// TODO Auto-generated method stub
		systemDao.delete(persistentInstance);
	}

	@Override
	public System findById(Integer id) {
		// TODO Auto-generated method stub
		return systemDao.findById(id);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return systemDao.findByProperty(propertyName, value);
	}

	@Override
	public List<System> findByExample(System instance) {
		// TODO Auto-generated method stub
		return systemDao.findByExample(instance);
	}

	@Override
	public List<System> findByName(Object name) {
		// TODO Auto-generated method stub
		return systemDao.findByName(name);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return systemDao.findAll();
	}

	@Override
	public List<System> findByListshow(Object listshow) {
		// TODO Auto-generated method stub
		return systemDao.findByListshow(listshow);
	}

}
