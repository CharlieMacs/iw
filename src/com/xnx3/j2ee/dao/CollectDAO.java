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

import com.xnx3.j2ee.entity.Collect;

/**
 * A data access object (DAO) providing persistence and search support for
 * Collect entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.Collect
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class CollectDAO {
	private static final Logger log = LoggerFactory.getLogger(CollectDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String OTHERSID = "othersid";
	public static final String ADDTIME = "addtime";

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

	public void save(Collect transientInstance) {
		log.debug("saving Collect instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Collect persistentInstance) {
		log.debug("deleting Collect instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Collect findById(java.lang.Integer id) {
		log.debug("getting Collect instance with id: " + id);
		try {
			Collect instance = (Collect) getCurrentSession().get(
					"com.xnx3.j2ee.entity.Collect", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Collect> findByExample(Collect instance) {
		log.debug("finding Collect instance by example");
		try {
			List<Collect> results = (List<Collect>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.Collect")
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
		log.debug("finding Collect instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Collect as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Collect> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<Collect> findByOthersid(Object othersid) {
		return findByProperty(OTHERSID, othersid);
	}

	public List<Collect> findByAddtime(Object addtime) {
		return findByProperty(ADDTIME, addtime);
	}

	public List findAll() {
		log.debug("finding all Collect instances");
		try {
			String queryString = "from Collect";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Collect merge(Collect detachedInstance) {
		log.debug("merging Collect instance");
		try {
			Collect result = (Collect) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Collect instance) {
		log.debug("attaching dirty Collect instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Collect instance) {
		log.debug("attaching clean Collect instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CollectDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CollectDAO) ctx.getBean("CollectDAO");
	}
}