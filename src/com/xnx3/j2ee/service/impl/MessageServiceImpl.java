package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.MessageDAO;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageDAO messageDao;
	
	@Override
	public void save(Message transientInstance) {
		// TODO Auto-generated method stub
		messageDao.save(transientInstance);
	}

	@Override
	public void delete(Message persistentInstance) {
		// TODO Auto-generated method stub
		messageDao.delete(persistentInstance);
	}

	@Override
	public Message findById(Integer id) {
		// TODO Auto-generated method stub
		return messageDao.findById(id);
	}

	@Override
	public List<Message> findByExample(Message instance) {
		// TODO Auto-generated method stub
		return messageDao.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return messageDao.findByProperty(propertyName, value);
	}

	@Override
	public List<Message> findBySelf(Object self) {
		// TODO Auto-generated method stub
		return messageDao.findBySelf(self);
	}

	@Override
	public List<Message> findByOther(Object other) {
		// TODO Auto-generated method stub
		return messageDao.findByOther(other);
	}

	@Override
	public List<Message> findByTime(Object time) {
		// TODO Auto-generated method stub
		return messageDao.findByTime(time);
	}

	@Override
	public List<Message> findByState(Object state) {
		// TODO Auto-generated method stub
		return messageDao.findByState(state);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return messageDao.findAll();
	}

	public List findAllAndData(){
		return messageDao.findAllAndData();
	}
}
