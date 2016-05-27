package com.xnx3.j2ee.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Sql;

/**
 * 公共查询，直接执行SQL
 * @author 管雷鸣
 *
 */
public interface GlobalService {

	/**
	 * 获取查询的信息条数
	 * @param tableName 表名,多个表名中间用,分割，如: "user,message,log"。同样如果是多个表，where参数需要增加关联条件
	 * @param appendWhere {@link Sql#getWhere(HttpServletRequest, String[], String)}
	 * @return
	 */
	public int count(String tableName,String where);

	/**
	 * 查询列表，配合 {@link Sql} 一块使用
	 * @param sql 组合好的查询{@link Sql}
	 * @param entityClass 转化为什么实体类
	 * @return List<Map<String,String>>
	 */
	public List findEntityBySql(Sql sql,Class entityClass);
	
	/**
	 * 查询数据列表
	 * @param sqlQuery 查询语句，如: SELECT * FROM user WHERE id < 10
	 * @param entityClass 转化为什么实体类
	 * @return List<entity>
	 */
	public List findEntityBySqlQuery(String sqlQuery, Class entityClass);
	
	/**
	 * 传入 {@link Sql} 查询List列表
	 * @param sql 组合好的{@link Sql}
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,Object>> findMapBySql(Sql sql);
	
	/**
	 * 添加/修改
	 * @param entity 实体类
	 */
	public void save(Object entity);
	
	/**
	 * 删除
	 * @param entity 实体类
	 */
	public void delete(Object entity);
	
	/**
	 * 根据主键查记录
	 * @param entity 实体类，如 {@link User}.class
	 * @param id 主键id
	 * @return Object 可直接转换为实体类
	 */
	public Object findById(Class c , int id);
	
	/**
	 * 根据实体类对象的赋值查纪录列表
	 * @param obj 实体类
	 * @return {@link List}
	 */
	public List findByExample(Object entity);
	
	/**
	 * 根据字段名查值
	 * @param c {@link Class} 实体类，如 {@link User}.class
	 * @param propertyName 数据表字段名
	 * @param value  值
	 * @return {@link List}
	 */
	public List findByProperty(Class c,String propertyName, Object value);
	
	/**
	 * 执行SQL语句
	 * @param sql 要执行的SQL语句
	 * @return
	 */
	public int executeSql(String sql);
	
	/**
	 * 数据表的某项数值+1
	 * @param tableName 数据表名称
	 * @param fieldName 执行＋1的项
	 * @param where 条件，如 id=5
	 */
	public void addOne(String tableName,String fieldName,String where);
	
	/**
	 * 数据表的某项数值-1
	 * @param tableName 数据表名称
	 * @param fieldName 执行＋1的项
	 * @param where 条件，如 id=5
	 */
	public void subtractOne(String tableName,String fieldName,String where);
}