package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.FriendDAO;
import com.xnx3.j2ee.entity.Friend;
import com.xnx3.j2ee.service.FriendService;

@Service("friendService")
public class FriendServiceImpl implements FriendService {
	
	@Resource
	private FriendDAO friendDAO;

	@Override
	public void save(Friend transientInstance) {
		// TODO Auto-generated method stub
		friendDAO.save(transientInstance);
	}

	@Override
	public void delete(Friend persistentInstance) {
		// TODO Auto-generated method stub
		friendDAO.delete(persistentInstance);
	}

	@Override
	public Friend findById(Integer id) {
		// TODO Auto-generated method stub
		return friendDAO.findById(id);
	}

	@Override
	public List<Friend> findByExample(Friend instance) {
		// TODO Auto-generated method stub
		return friendDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return friendDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Friend> findBySelf(Object self) {
		// TODO Auto-generated method stub
		return friendDAO.findBySelf(self);
	}

	@Override
	public List<Friend> findByOther(Object other) {
		// TODO Auto-generated method stub
		return friendDAO.findByOther(other);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return friendDAO.findAll();
	}

	@Override
	public Friend merge(Friend detachedInstance) {
		// TODO Auto-generated method stub
		return friendDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(Friend instance) {
		// TODO Auto-generated method stub
		friendDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(Friend instance) {
		// TODO Auto-generated method stub
		friendDAO.attachClean(instance);
	}

}
