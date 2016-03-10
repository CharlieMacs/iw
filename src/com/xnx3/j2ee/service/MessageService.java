package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Message;

/**
 * 站内信
 * @author 管雷鸣
 *
 */
public interface MessageService {

	public void save(Message transientInstance);

	public void delete(Message persistentInstance);

	public Message findById(java.lang.Integer id);

	public List<Message> findByExample(Message instance);

	public List findByProperty(String propertyName, Object value);

	public List<Message> findBySelf(Object self);

	public List<Message> findByOther(Object other);

	public List<Message> findByTime(Object time);

	public List<Message> findByState(Object state);

	public List findAll();

	public List findAllAndData();
}