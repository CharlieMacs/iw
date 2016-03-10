package com.xnx3.j2ee.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import com.xnx3.j2ee.util.Page;
import com.xnx3.j2ee.util.Sql;

/**
 * 通用的
 * @author 管雷鸣
 */
@Transactional
public class GlobalDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}
	
	/**
	 * 获取查询的信息条数
	 * @param tableName 表名
	 * @param where 查询条件，直接使用 {@link Sql#getWhere(javax.servlet.http.HttpServletRequest, String[], String)} 来组合
	 * @return
	 */
	public int count(String tableName,String where){
		String queryString = "SELECT count(id) FROM "+tableName+where;
		BigInteger count = (BigInteger)getCurrentSession().createSQLQuery(queryString).uniqueResult();
		return count.intValue();
	}

	/**
	 * 查询列表，配合 {@link Page} {@link Sql} 一块使用
	 * @param selectFrom 如 SELECT * FROM user
	 * @param where {@link Sql#getWhere(javax.servlet.http.HttpServletRequest, String[], String)}
	 * @param limitStart limit开始的记录数
	 * @param limitNumber limit返回多少条记录
	 * @param entityClass 转化为什么实体类
	 * @return
	 */
	public List findBySqlQuery(String selectFrom,String where,int limitStart,int limitNumber,Class entityClass) {
		try {
			String queryString = selectFrom+where+" LIMIT "+limitStart+","+limitNumber;
			Query queryObject = getCurrentSession().createSQLQuery(queryString).addEntity(entityClass);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 同 {@link #findBySqlQuery(String, String, int, int, Class)}
	 * 返回的是<List<Map<String,Object>>>
	 * @param sql 执行的sql，不包含limit，limit会自动拼接
	 * @param limitStart 
	 * @param limitNumber
	 * @return 
	 */
	public List<Map<String,String>> findBySqlQuery(String sql,int limitStart,int limitNumber) {
		try {
			String queryString = sql+" LIMIT "+limitStart+","+limitNumber;
			Query queryObject = getCurrentSession().createSQLQuery(queryString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public static GlobalDAO getFromApplicationContext(ApplicationContext ctx) {
		return (GlobalDAO) ctx.getBean("GlobalDAO");
	}
}