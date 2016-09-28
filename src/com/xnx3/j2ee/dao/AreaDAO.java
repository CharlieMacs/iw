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

import com.xnx3.j2ee.entity.Area;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.entity.Permission;

/**
 * A data access object (DAO) providing persistence and search support for Paint
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.Paint
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class AreaDAO {
	private static final Logger log = LoggerFactory.getLogger(LogDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String DISTRICT = "district";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";

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
		log.debug("saving Area instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Area persistentInstance) {
		log.debug("deleting area instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Area findById(java.lang.Integer id) {
		log.debug("getting Area instance with id: " + id);
		try {
			Area instance = (Area) getCurrentSession().get(
					"com.xnx3.j2ee.entity.Area", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Area> findByExample(Area instance) {
		log.debug("finding Area instance by example");
		try {
			List<Area> results = (List<Area>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.Area")
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
		log.debug("finding Area instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Area as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Area> findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}
	public List<Area> findByCity(Object city) {
		return findByProperty(CITY, city);
	}
	public List<Area> findByDistrict(Object district) {
		return findByProperty(DISTRICT, district);
	}
	public List<Area> findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}
	public List<Area> findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List findAll() {
		log.debug("finding all Paint instances");
		try {
			String queryString = "from Area";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Area merge(Area detachedInstance) {
		log.debug("merging Area instance");
		try {
			Area result = (Area) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Area instance) {
		log.debug("attaching dirty Area instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Area instance) {
		log.debug("attaching clean Area instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public static AreaDAO getFromApplicationContext(ApplicationContext ctx) {
		return (AreaDAO) ctx.getBean("AreaDAO");
	}
}