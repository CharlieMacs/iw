package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Log;

/**
 * 日志
 * @author 管雷鸣
 *
 */
public interface LogService {

	public void save(Log transientInstance);

	public void delete(Log persistentInstance);

	public Log findById(java.lang.Integer id);

	public List<Log> findByExample(Log instance);

	public List findByProperty(String propertyName, Object value);

	public List<Log> findByUserid(Object userid);

	public List<Log> findByType(Object type);

	public List<Log> findByGoalid(Object goalid);

	public List<Log> findByDelete(Object delete);

	public List findAll();

	public Log merge(Log detachedInstance);

	public void attachDirty(Log instance);

	public void attachClean(Log instance);

}