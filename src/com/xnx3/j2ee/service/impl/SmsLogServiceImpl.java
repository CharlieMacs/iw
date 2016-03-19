package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.SmsLogDAO;
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.service.SmsLogService;

@Service("smsLogService")
public class SmsLogServiceImpl implements SmsLogService {

	@Resource
	private SmsLogDAO smsLogDAO;
	
	@Override
	public void save(SmsLog transientInstance) {
		// TODO Auto-generated method stub
		smsLogDAO.save(transientInstance);
	}

	@Override
	public void delete(SmsLog persistentInstance) {
		// TODO Auto-generated method stub
		smsLogDAO.delete(persistentInstance);
	}

	@Override
	public SmsLog findById(Integer id) {
		// TODO Auto-generated method stub
		return smsLogDAO.findById(id);
	}

	@Override
	public List<SmsLog> findByExample(SmsLog instance) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<SmsLog> findByCode(Object code) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByCode(code);
	}

	@Override
	public List<SmsLog> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByUserid(userid);
	}

	@Override
	public List<SmsLog> findByUsed(Object used) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByUsed(used);
	}

	@Override
	public List<SmsLog> findByType(Object type) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByType(type);
	}

	@Override
	public List<SmsLog> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByAddtime(addtime);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return smsLogDAO.findAll();
	}

	@Override
	public SmsLog merge(SmsLog detachedInstance) {
		// TODO Auto-generated method stub
		return smsLogDAO.merge(detachedInstance);
	}

}
