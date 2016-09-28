package com.xnx3.j2ee.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import com.xnx3.j2ee.entity.Role;
import com.xnx3.j2ee.entity.UserRole;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.User
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class UserRoleDAO {
	private static final Logger log = LoggerFactory.getLogger(UserRoleDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String USERID = "userid";
	public static final String ROLEID = "roleid";

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

	public void save(UserRole transientInstance) {
		log.debug("saving User instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserRole persistentInstance) {
		log.debug("deleting User instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserRole findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			UserRole instance = (UserRole) getCurrentSession().get(
					"com.xnx3.j2ee.entity.UserRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserRole as model where model."
					+ propertyName + "= ?0 ";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<UserRole> findByExample(UserRole instance) {
		log.debug("finding User instance by example");
		try {
			List<UserRole> results = (List<UserRole>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.UserRole")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<UserRole> findByUserId(Object userid) {
		return findByProperty(USERID, userid);
	}
	
	/**
	 * 根据用户id，得到用户有哪些角色
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleByUserId(Integer userid) {
		try {
			String queryString = "SELECT role.* FROM user_role,role WHERE user_role.roleid=role.id AND user_role.userid= ?0 ";
			Query queryObject = getCurrentSession().createSQLQuery(queryString).addEntity(Role.class);
			queryObject.setParameter("0", userid);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from UserRole";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public static UserRoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserRoleDAO) ctx.getBean("UserRoleDAO");
	}
}