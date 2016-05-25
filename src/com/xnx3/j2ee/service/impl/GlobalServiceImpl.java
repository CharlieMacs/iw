package com.xnx3.j2ee.service.impl;

import java.util.List;
import java.util.Map;

import com.xnx3.j2ee.dao.GlobalDAO;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

public class GlobalServiceImpl implements GlobalService {
	private GlobalDAO globalDAO;
	
	public GlobalDAO getGlobalDAO() {
		return globalDAO;
	}

	public void setGlobalDAO(GlobalDAO globalDAO) {
		this.globalDAO = globalDAO;
	}

	@Override
	public int count(String tableName, String where) {
		// TODO Auto-generated method stub
		return globalDAO.count(tableName, where);
	}

//	@Override
//	public List findBySqlQuery(String selectFrom, String where, Page page,Class entityClass) {
//		return globalDAO.findBySqlQuery(selectFrom, where, page,entityClass);
//	}

//	@Override
//	public List<Map<String, String>> findBySqlQuery(String sql, Page page) {
//		return globalDAO.findBySqlQuery(sql,page);
//	}

	@Override
	public List findEntityBySql(Sql sql, Class entityClass) {
		// TODO Auto-generated method stub
		return globalDAO.findEntityBySqlQuery(sql, entityClass);
	}

	@Override
	public List<Map<String, String>> findMapBySql(Sql sql) {
		// TODO Auto-generated method stub
		return globalDAO.findMapBySql(sql);
	}

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		globalDAO.save(entity);
	}

	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		globalDAO.delete(entity);
	}

	@Override
	public Object findById(Class c, int id) {
		// TODO Auto-generated method stub
		return globalDAO.findById(c, id);
	}

	@Override
	public List findByExample(Object entity) {
		// TODO Auto-generated method stub
		return globalDAO.findByExample(entity);
	}

	@Override
	public List findByProperty(Class c, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return globalDAO.findByProperty(c, propertyName, value);
	}

	@Override
	public int executeSql(String sql) {
		// TODO Auto-generated method stub
		return globalDAO.executeSql(sql);
	}

	@Override
	public void addOne(String tableName, String fieldName, String where) {
		// TODO Auto-generated method stub
		globalDAO.executeSql("UPDATE "+tableName+" SET "+fieldName+" = "+fieldName+"+1 WHERE "+where);
	}

}
