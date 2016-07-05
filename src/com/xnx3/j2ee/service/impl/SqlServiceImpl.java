package com.xnx3.j2ee.service.impl;

import java.util.List;
import java.util.Map;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.util.Sql;

public class SqlServiceImpl implements SqlService {
	private SqlDAO sqlDAO;
	
	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public int count(String tableName, String where) {
		// TODO Auto-generated method stub
		return sqlDAO.count(tableName, where);
	}

	@Override
	public List findEntityBySql(Sql sql, Class entityClass) {
		// TODO Auto-generated method stub
		return sqlDAO.findEntityBySqlQuery(sql.getSql(), entityClass);
	}

	@Override
	public List findEntityBySqlQuery(String sqlQuery, Class entityClass) {
		// TODO Auto-generated method stub
		return sqlDAO.findEntityBySqlQuery(sqlQuery, entityClass);
	}
	
	
	@Override
	public List<Map<String, Object>> findMapBySql(Sql sql) {
		// TODO Auto-generated method stub
		return sqlDAO.findMapBySql(sql);
	}

	public List<Map<String,Object>> findMapBySqlQuery(String sqlQuery){
		return sqlDAO.findMapBySqlQuery(sqlQuery);
	}
	
	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		sqlDAO.save(entity);
	}

	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub
		sqlDAO.delete(entity);
	}

	@Override
	public Object findById(Class c, int id) {
		// TODO Auto-generated method stub
		return sqlDAO.findById(c, id);
	}

	@Override
	public List findByExample(Object entity) {
		// TODO Auto-generated method stub
		return sqlDAO.findByExample(entity);
	}

	@Override
	public List findByProperty(Class c, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return sqlDAO.findByProperty(c, propertyName, value);
	}

	@Override
	public int executeSql(String sql) {
		return sqlDAO.executeSql(sql);
	}

	@Override
	public void addOne(String tableName, String fieldName, String where) {
		sqlDAO.addOne(tableName, fieldName, where);
	}

	@Override
	public void subtractOne(String tableName, String fieldName, String where) {
		sqlDAO.subtractOne(tableName, fieldName, where);
	}

}
