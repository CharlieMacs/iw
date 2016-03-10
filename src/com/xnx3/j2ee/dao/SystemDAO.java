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
public class SystemDAO {
	private static final Logger log = LoggerFactory.getLogger(UserRoleDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LISTSHOW = "listshow";

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

	public void save(com.xnx3.j2ee.entity.System transientInstance) {
		log.debug("saving System instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(com.xnx3.j2ee.entity.System persistentInstance) {
		log.debug("deleting com.xnx3.j2ee.entity.System instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public com.xnx3.j2ee.entity.System findById(java.lang.Integer id) {
		log.debug("getting com.xnx3.j2ee.entity.System instance with id: " + id);
		try {
			com.xnx3.j2ee.entity.System instance = (com.xnx3.j2ee.entity.System) getCurrentSession().get(
					"com.xnx3.j2ee.entity.System", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public List findByProperty(String propertyName, Object value) {
		log.debug("finding com.xnx3.j2ee.entity.System instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from System as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<com.xnx3.j2ee.entity.System> findByExample(com.xnx3.j2ee.entity.System instance) {
		log.debug("finding com.xnx3.j2ee.entity.System instance by example");
		try {
			List<com.xnx3.j2ee.entity.System> results = (List<com.xnx3.j2ee.entity.System>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.System")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<com.xnx3.j2ee.entity.System> findByName(Object name) {
		return findByProperty(NAME, name);
	}
	
	public List<com.xnx3.j2ee.entity.System> findByListshow(Object listshow) {
		return findByProperty(LISTSHOW, listshow);
	}


	public List findAll() {
		log.debug("finding all com.xnx3.j2ee.entity.System instances");
		try {
			String queryString = "from System";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public static SystemDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SystemDAO) ctx.getBean("SystemDAO");
	}
}