package com.xnx3.j2ee.dao;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import com.xnx3.j2ee.entity.Log;

/**
 * A data access object (DAO) providing persistence and search support for Paint
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.Log
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class LogDAO {
	private static final Logger log = LoggerFactory.getLogger(LogDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String USERID = "userid";
	public static final String TYPE = "type";
	public static final String GOALID = "goalid";
	public static final String VALUE = "value";
	public static final String DELETE = "delete";

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

	public void save(Log transientInstance) {
		log.debug("saving Log instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Log persistentInstance) {
		log.debug("deleting Paint instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Log findById(java.lang.Integer id) {
		log.debug("getting Log instance with id: " + id);
		try {
			Log instance = (Log) getCurrentSession().get(
					"com.xnx3.j2ee.entity.Log", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Log> findByExample(Log instance) {
		log.debug("finding Log instance by example");
		try {
			List<Log> results = (List<Log>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.Log")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Log instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Log as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Log> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<Log> findByType(Object type) {
		return findByProperty(TYPE, type);
	}
	
	public List<Log> findByGoalid(Object goalid) {
		return findByProperty(GOALID, goalid);
	}


	public List<Log> findByDelete(Object delete) {
		return findByProperty(DELETE, delete);
	}

	public List findAll() {
		log.debug("finding all Paint instances");
		try {
			String queryString = "from Log";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Log merge(Log detachedInstance) {
		log.debug("merging Log instance");
		try {
			Log result = (Log) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Log instance) {
		log.debug("attaching dirty Log instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Log instance) {
		log.debug("attaching clean Log instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (LogDAO) ctx.getBean("LogDAO");
	}
}