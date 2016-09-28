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
import com.xnx3.j2ee.entity.Friend;

/**
 * A data access object (DAO) providing persistence and search support for
 * Friend entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.Friend
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class FriendDAO {
	private static final Logger log = LoggerFactory.getLogger(FriendDAO.class);
	// property constants
	public static final String SELF = "self";
	public static final String OTHER = "other";

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

	public void save(Friend transientInstance) {
		log.debug("saving Friend instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Friend persistentInstance) {
		log.debug("deleting Friend instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Friend findById(java.lang.Integer id) {
		log.debug("getting Friend instance with id: " + id);
		try {
			Friend instance = (Friend) getCurrentSession().get(
					"com.xnx3.j2ee.entity.Friend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Friend> findByExample(Friend instance) {
		log.debug("finding Friend instance by example");
		try {
			List<Friend> results = (List<Friend>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.Friend")
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
		log.debug("finding Friend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Friend as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Friend> findBySelf(Object self) {
		return findByProperty(SELF, self);
	}

	public List<Friend> findByOther(Object other) {
		return findByProperty(OTHER, other);
	}

	public List findAll() {
		log.debug("finding all Friend instances");
		try {
			String queryString = "from Friend";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Friend merge(Friend detachedInstance) {
		log.debug("merging Friend instance");
		try {
			Friend result = (Friend) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Friend instance) {
		log.debug("attaching dirty Friend instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Friend instance) {
		log.debug("attaching clean Friend instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FriendDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FriendDAO) ctx.getBean("FriendDAO");
	}
}