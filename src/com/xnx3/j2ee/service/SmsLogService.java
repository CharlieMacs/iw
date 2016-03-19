package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.SmsLog;

public interface SmsLogService {

	public void save(SmsLog transientInstance);

	public void delete(SmsLog persistentInstance);

	public SmsLog findById(java.lang.Integer id);

	public List<SmsLog> findByExample(SmsLog instance);

	public List findByProperty(String propertyName, Object value);

	public List<SmsLog> findByCode(Object code);

	public List<SmsLog> findByUserid(Object userid);

	public List<SmsLog> findByUsed(Object used);

	public List<SmsLog> findByType(Object type);

	public List<SmsLog> findByAddtime(Object addtime);

	public List findAll();

	public SmsLog merge(SmsLog detachedInstance);

}