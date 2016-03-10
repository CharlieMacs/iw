package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.MessageData;

/**
 * 站内信息内容
 * @author 管雷鸣
 *
 */
public interface MessageDataService {

	public void save(MessageData transientInstance);

	public void delete(MessageData persistentInstance);

	public MessageData findById(java.lang.Integer id);

	public List<MessageData> findByExample(MessageData instance);

	public List findByProperty(String propertyName, Object value);

	public List<MessageData> findByContent(Object content);

	public List findAll();

	public MessageData merge(MessageData detachedInstance);

	public void attachDirty(MessageData instance);

	public void attachClean(MessageData instance);

}