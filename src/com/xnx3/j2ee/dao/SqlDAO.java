package com.xnx3.j2ee.dao;

import static org.hibernate.criterion.Example.create;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.xnx3.StringUtil;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.util.Sql;

/**
 * 通用的
 * @author 管雷鸣
 */
@Transactional
public class SqlDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}
	
	/**
	 * 获取查询的信息条数
	 * @param tableName 表名,多个表中间用,分割，如: "user,message,log"。同样如果是多个表，where参数需要增加关联条件
	 * @param where 查询条件，传入如“WHERE id > 1”可直接使用 {@link Sql#getWhere(javax.servlet.http.HttpServletRequest, String[], String)} 来组合
	 * @return 统计条数
	 */
	public int count(String tableName,String where){
		String queryString = "SELECT count(*) FROM "+tableName+" "+where;
		BigInteger count = (BigInteger)getCurrentSession().createSQLQuery(queryString).uniqueResult();
		return count.intValue();
	}

	/**
	 * 传入 {@link Sql} 查询List列表
	 * @param sql 组合好的{@link Sql}
	 * @return List Map<String,String>
	 */
	public List<Map<String,Object>> findMapBySql(Sql sql){
		try {
			Query queryObject = getCurrentSession().createSQLQuery(sql.getSql()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 传入查询的原生SQL语句
	 * @param sqlQuery 原生SQL语句
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,Object>> findMapBySqlQuery(String sqlQuery){
		try {
			Query queryObject = getCurrentSession().createSQLQuery(sqlQuery).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 通过原生SQL语句查询,返回List实体类
	 * @param sql 原生SQL查询语句
	 * @param entityClass 转化为什么实体类输出
	 * @return List 实体类列表
	 */
	public <E> List<E> findBySqlQuery(String sqlQuery,Class<E> entityClass) {
		try {
			Query queryObject = getCurrentSession().createSQLQuery(sqlQuery).addEntity(entityClass);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 传入原生SQL语句，查询返回一个实体类。 会自动在原生SQL语句末尾添加 LIMIT 0,1 进行组合查询语句
	 * @param sqlQuery 查询语句，如 SELECT * FROM user WHERE username = 'xnx3'
	 * @param entityClass 要转换为什么实体类返回，如 User.class
	 * @return 若查询到，返回查询到的对象(需强制转化为想要的实体类)，若查询不到，返回null
	 */
	public <E> E findAloneBySqlQuery(String sqlQuery,Class<E> entityClass){
		if(sqlQuery.toUpperCase().indexOf(" LIMIT ") == -1){
			sqlQuery = sqlQuery + " LIMIT 0,1";
		}
		List<E> list = findBySqlQuery(sqlQuery, entityClass);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 添加/修改
	 * @param entity 实体类
	 */
	public void save(Object entity) {
		try {
			getCurrentSession().saveOrUpdate(entity);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 删除
	 * @param entity 实体类
	 */
	public void delete(Object entity) {
		try {
			getCurrentSession().delete(entity);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 根据主键查记录
	 * @param entity 实体类 如 {@link User}.class
	 * @param id 主键id
	 * @return 实体类
	 */
	public <E> E findById(Class<E> c , int id) {
		try {
			Object instance = getCurrentSession().get(c.getCanonicalName(), id);
			return (E) instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 根据实体类对象的赋值查纪录列表
	 * @param obj 实体类
	 * @return List 实体类
	 */
	public <E> List<E> findByExample(Object entity) {
		try {
			List results = getCurrentSession()
					.createCriteria(entity.getClass().getCanonicalName())
					.add(create(entity)).list();
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 根据字段名查值
	 * @param c {@link Class} 实体类，如 {@link User}.class
	 * @param propertyName 数据表字段名(Hibernate 语句的字段名)
	 * @param value 值
	 * @return {@link List} 实体类
	 */
	public <E> List<E> findByProperty(Class<E> c,String propertyName, Object value) {
		try {
			String queryString = "from "+c.getSimpleName()+" as model where model."
					+ propertyName + "= ?0 ";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 执行原生SQL语句
	 * @param sql 要执行的SQL语句
	 * @return query.executeUpdate()的返回值
	 */
	public int executeSql(String sql){    
        int result ;    
        SQLQuery query = getCurrentSession().createSQLQuery(sql);    
        result = query.executeUpdate();    
        return result;
    }
	
	/**
	 * 数据表的某项数值+1
	 * @param tableName 数据表名称
	 * @param fieldName 执行＋1的项
	 * @param where 条件，如 id=5
	 */
	public void addOne(String tableName, String fieldName, String where) {
		executeSql("UPDATE "+tableName+" SET "+fieldName+" = "+fieldName+"+1 WHERE "+where);
	}

	/**
	 * 数据表的某项数值-1
	 * @param tableName 数据表名称
	 * @param fieldName 执行＋1的项
	 * @param where 条件，如 id=5
	 */
	public void subtractOne(String tableName, String fieldName, String where) {
		executeSql("UPDATE "+tableName+" SET "+fieldName+" = "+fieldName+"-1 WHERE "+where);
	}
	

	/**
	 * 查询列表,返回实体类 List<Entity>
	 * @param tableName 要查询哪个数据表的所有数据
	 * @param entityClass 转化为什么实体类
	 * @return List<Entity>
	 */
	public <E> List<E> findAll(Class<E> entityClass) {
		try {
			Query queryObject = getCurrentSession().createSQLQuery("SELECT * FROM "+getDatabaseTableName(entityClass)).addEntity(entityClass);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public static SqlDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SqlDAO) ctx.getBean("SqlDAO");
	}
	

	/**
	 * 从JPA的实体类中，获取数据库表的名字
	 * @param c 实体类，如 User.class
	 * @return 此实体类的数据表的原名
	 */
	public static String getDatabaseTableName(Class c){
		Table table = (Table) c.getAnnotation(javax.persistence.Table.class);
		String tableName = null;
		if(table != null && table.name() != null && table.name().length() > 0){
			tableName = table.name();
		}else{
			tableName = StringUtil.firstCharToLowerCase(c.getSimpleName());
		}
		return tableName;
	}
}