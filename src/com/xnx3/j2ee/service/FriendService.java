package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Friend;

/**
 * 好友
 * @author 管雷鸣
 *
 */
public interface FriendService {

	public void save(Friend transientInstance);

	public void delete(Friend persistentInstance);

	public Friend findById(java.lang.Integer id);

	public List<Friend> findByExample(Friend instance);

	public List findByProperty(String propertyName, Object value);

	public List<Friend> findBySelf(Object self);

	public List<Friend> findByOther(Object other);

	public List findAll();

	public Friend merge(Friend detachedInstance);

	public void attachDirty(Friend instance);

	public void attachClean(Friend instance);

}