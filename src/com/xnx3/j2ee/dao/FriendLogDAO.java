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

import com.xnx3.j2ee.entity.FriendLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * FriendLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.FriendLog
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class FriendLogDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FriendLogDAO.class);
	// property constants
	public static final String SELF = "self";
	public static final String OTHER = "other";
	public static final String TIME = "time";
	public static final String STATE = "state";
	public static final String IP = "ip";

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

	public void save(FriendLog transientInstance) {
		log.debug("saving FriendLog instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FriendLog persistentInstance) {
		log.debug("deleting FriendLog instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FriendLog findById(java.lang.Integer id) {
		log.debug("getting FriendLog instance with id: " + id);
		try {
			FriendLog instance = (FriendLog) getCurrentSession().get(
					"com.xnx3.j2ee.entity.FriendLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FriendLog> findByExample(FriendLog instance) {
		log.debug("finding FriendLog instance by example");
		try {
			List<FriendLog> results = (List<FriendLog>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.FriendLog")
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
		log.debug("finding FriendLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FriendLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FriendLog> findBySelf(Object self) {
		return findByProperty(SELF, self);
	}

	public List<FriendLog> findByOther(Object other) {
		return findByProperty(OTHER, other);
	}

	public List<FriendLog> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<FriendLog> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<FriendLog> findByIp(Object ip) {
		return findByProperty(IP, ip);
	}

	public List findAll() {
		log.debug("finding all FriendLog instances");
		try {
			String queryString = "from FriendLog";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FriendLog merge(FriendLog detachedInstance) {
		log.debug("merging FriendLog instance");
		try {
			FriendLog result = (FriendLog) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FriendLog instance) {
		log.debug("attaching dirty FriendLog instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FriendLog instance) {
		log.debug("attaching clean FriendLog instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FriendLogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FriendLogDAO) ctx.getBean("FriendLogDAO");
	}
}