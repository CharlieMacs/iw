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
	
	/**
	 * 写日志
	 * @param goalid 操作的目标id
	 * @param type 日志分类，传入 SystemConfig.xml中logTypeList节点配置的type-程序内调用的名字
	 * @param value 描述的内容，自动截取前20个字符
	 */
	public void insert(int goalid, String type, String value);
	
	/**
	 * 写日志
	 * @param userid 此日志所属的用户id
	 * @param goalid 操作的目标id
	 * @param type 日志分类，传入 SystemConfig.xml中logTypeList节点配置的type-程序内调用的名字
	 * @param value 描述的内容，自动截取前20个字符
	 */
	public void insert(int userid, int goalid, String type, String value);
	
	/**
	 * 写日志
	 * @param type 日志分类，传入 SystemConfig.xml中logTypeList节点配置的type-程序内调用的名字
	 * @param value 描述的内容，自动截取前20个字符
	 */
	public void insert(String type, String value);
	
	/**
	 * 写日志
	 * @param goalid 操作的目标id
	 * @param type 日志分类，传入 SystemConfig.xml中logTypeList节点配置的type-程序内调用的名字
	 */
	public void insert(int goalid, String type);
	
	/**
	 * 写日志
	 * @param type 日志分类，传入 SystemConfig.xml中logTypeList节点配置的type-程序内调用的名字
	 */
	public void insert(String type);
	
}