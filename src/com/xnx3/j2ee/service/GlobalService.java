package com.xnx3.j2ee.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.util.Page;
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
	 * 查询列表，配合 {@link Page} {@link Sql} 一块使用
	 * @param selectFrom 如 SELECT * FROM user
	 * @param where {@link Sql#getWhere(javax.servlet.http.HttpServletRequest, String[], String)}
	 * @param page {@link Page}
	 * @param entityClass 自动转化为的实体类
	 * @return
	 */
	public List findBySqlQuery(String selectFrom,String where,Page page,Class entityClass);
	
	/**
	 * 同 {@link #findBySqlQuery(String, String, int, int, Class)}
	 * 返回的是List<Map<String,String>>
	 * @param sql 执行的sql，不包含limit，limit会自动拼接
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,String>> findBySqlQuery(String sql,Page page);
}