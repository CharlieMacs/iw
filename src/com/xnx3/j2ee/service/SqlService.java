package com.xnx3.j2ee.service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Sql;

/**
 * 公共查询，直接执行SQL
 * @author 管雷鸣
 *
 */
public interface SqlService {

	/**
	 * 获取查询的信息条数
	 * @param tableName 表名,多个表名中间用,分割，如: "user,message,log"。同样如果是多个表，where参数需要增加关联条件
	 * @param where 查询条件，传入如“WHERE id > 1” ；若没有查询条件，则可以传入null或者""空字符串
	 * @return 统计条数
	 */
	public int count(String tableName,String where);

	/**
	 * 查询列表，通过 {@link Sql} 自动生成查询语句查询信息列表,返回List实体类。通常用于分页列表
	 * @param sql 组合好的查询{@link Sql}
	 * @param entityClass 转化为什么实体类返回
	 * @return List 实体类列表
	 */
	public <E> List<E> findBySql(Sql sql, Class<E> entityClass);
	
	/**
	 * 通过原生SQL语句查询,返回List实体类
	 * @param sql 原生SQL查询语句
	 * @param entityClass 转化为什么实体类输出
	 * @return List 实体类列表
	 */
	public <E> List<E> findBySqlQuery(String sqlQuery, Class<E> entityClass);
	
	/**
	 * 传入原生SQL语句，查询返回一个实体类。 会自动在原生SQL语句末尾添加 LIMIT 0,1 进行组合查询语句
	 * @param sqlQuery 查询语句，如 SELECT * FROM user WHERE username = 'xnx3'
	 * @param entityClass 要转换为什么实体类返回，如 User.class
	 * @return 若查询到，返回查询到的对象(需强制转化为想要的实体类)，若查询不到，返回null
	 */
	public <E> E findAloneBySqlQuery(String sqlQuery,Class<E> entityClass);
	
	/**
	 * 传入 {@link Sql} 查询List列表
	 * @param sql 组合好的{@link Sql}
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,Object>> findMapBySql(Sql sql);
	
	/**
	 * 传入查询的SQL语句
	 * @param sqlQuery SQL语句
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,Object>> findMapBySqlQuery(String sqlQuery);
	
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
	 * @return 实体类
	 */
	public <E> E findById(Class<E> c , int id);
	
	/**
	 * 根据实体类对象的赋值查纪录列表
	 * @param obj 实体类
	 * @return List 实体类
	 */
	public <E> List<E> findByExample(Object entity);
	
	/**
	 * 根据字段名查值
	 * @param c {@link Class} 实体类，如 {@link User}.class
	 * @param propertyName 数据表字段名(Hibernate 语句的字段名)
	 * @param value 值
	 * @return {@link List} 实体类
	 */
	public <E> List<E> findByProperty(Class<E> c,String propertyName, Object value);
	
	/**
	 * 执行原生SQL语句
	 * @param sql 要执行的SQL语句
	 * @return query.executeUpdate()的返回值，即Sql语句成功更新的条数
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

	/**
	 * 查询某个数据表的所有信息, 返回实体类列表 List<Entity> 
	 * <br/>相当于 SELECT * FROM tableName
	 * @param entityClass 从哪个实体类关联的数据表取数据，转化为什么实体类。如，传入 User.class
	 * @return List<Entity>
	 */
	public <E> List<E> findAll(Class<E> entityClass);
	
	/**
	 * 获取当前Hibernate的Session对象
	 * @return {@link Session}当前hibernate的Session
	 */
	public Session getCurrentSession();
}