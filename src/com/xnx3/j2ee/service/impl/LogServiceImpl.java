package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.MessageDataDAO;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDAO logDAO;
	
	@Override
	public void save(Log transientInstance) {
		// TODO Auto-generated method stub
		logDAO.save(transientInstance);
	}

	@Override
	public void delete(Log persistentInstance) {
		// TODO Auto-generated method stub
		logDAO.delete(persistentInstance);
	}

	@Override
	public Log findById(Integer id) {
		// TODO Auto-generated method stub
		return logDAO.findById(id);
	}

	@Override
	public List<Log> findByExample(Log instance) {
		// TODO Auto-generated method stub
		return logDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return logDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Log> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return logDAO.findByUserid(userid);
	}

	@Override
	public List<Log> findByType(Object type) {
		// TODO Auto-generated method stub
		return logDAO.findByType(type);
	}

	@Override
	public List<Log> findByGoalid(Object goalid) {
		// TODO Auto-generated method stub
		return logDAO.findByGoalid(goalid);
	}

	@Override
	public List<Log> findByDelete(Object delete) {
		// TODO Auto-generated method stub
		return logDAO.findByDelete(delete);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return logDAO.findAll();
	}

	@Override
	public Log merge(Log detachedInstance) {
		// TODO Auto-generated method stub
		return logDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(Log instance) {
		// TODO Auto-generated method stub
		logDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(Log instance) {
		// TODO Auto-generated method stub
		logDAO.attachClean(instance);
	}

}
