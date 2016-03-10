package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.MessageDataDAO;
import com.xnx3.j2ee.entity.MessageData;
import com.xnx3.j2ee.service.MessageDataService;

@Service("messageDataService")
public class MessageDataServiceImpl implements MessageDataService {

	@Resource
	private MessageDataDAO messageDataDAO;
	
	@Override
	public void save(MessageData transientInstance) {
		// TODO Auto-generated method stub
		messageDataDAO.save(transientInstance);
	}

	@Override
	public void delete(MessageData persistentInstance) {
		// TODO Auto-generated method stub
		messageDataDAO.delete(persistentInstance);
	}

	@Override
	public MessageData findById(Integer id) {
		// TODO Auto-generated method stub
		return messageDataDAO.findById(id);
	}

	@Override
	public List<MessageData> findByExample(MessageData instance) {
		// TODO Auto-generated method stub
		return messageDataDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return messageDataDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<MessageData> findByContent(Object content) {
		// TODO Auto-generated method stub
		return messageDataDAO.findByContent(content);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return messageDataDAO.findAll();
	}

	@Override
	public MessageData merge(MessageData detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attachDirty(MessageData instance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attachClean(MessageData instance) {
		// TODO Auto-generated method stub

	}

}
