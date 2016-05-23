package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.FriendLogDAO;
import com.xnx3.j2ee.entity.FriendLog;
import com.xnx3.j2ee.service.FriendLogService;

public class FriendLogServiceImpl implements FriendLogService {

	private FriendLogDAO friendLogDAO;
	
	public FriendLogDAO getFriendLogDAO() {
		return friendLogDAO;
	}

	public void setFriendLogDAO(FriendLogDAO friendLogDAO) {
		this.friendLogDAO = friendLogDAO;
	}

	@Override
	public void save(FriendLog transientInstance) {
		// TODO Auto-generated method stub
		friendLogDAO.save(transientInstance);
	}

	@Override
	public void delete(FriendLog persistentInstance) {
		// TODO Auto-generated method stub
		friendLogDAO.delete(persistentInstance);
	}

	@Override
	public FriendLog findById(Integer id) {
		// TODO Auto-generated method stub
		return friendLogDAO.findById(id);
	}

	@Override
	public List<FriendLog> findByExample(FriendLog instance) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<FriendLog> findBySelf(Object self) {
		// TODO Auto-generated method stub
		return friendLogDAO.findBySelf(self);
	}

	@Override
	public List<FriendLog> findByOther(Object other) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByOther(other);
	}

	@Override
	public List<FriendLog> findByTime(Object time) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByTime(time);
	}

	@Override
	public List<FriendLog> findByState(Object state) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByState(state);
	}

	@Override
	public List<FriendLog> findByIp(Object ip) {
		// TODO Auto-generated method stub
		return friendLogDAO.findByIp(ip);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return friendLogDAO.findAll();
	}

	@Override
	public FriendLog merge(FriendLog detachedInstance) {
		// TODO Auto-generated method stub
		return friendLogDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(FriendLog instance) {
		// TODO Auto-generated method stub
		friendLogDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(FriendLog instance) {
		// TODO Auto-generated method stub
		friendLogDAO.attachClean(instance);
	}

}
