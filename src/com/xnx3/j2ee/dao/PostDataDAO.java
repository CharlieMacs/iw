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
import com.xnx3.j2ee.entity.PostData;

/**
 * A data access object (DAO) providing persistence and search support for
 * PostData entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.PostData
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PostDataDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PostDataDAO.class);
	// property constants
	public static final String TEXT = "text";

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

	public void save(PostData transientInstance) {
		log.debug("saving PostData instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PostData persistentInstance) {
		log.debug("deleting PostData instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PostData findById(java.lang.Integer id) {
		log.debug("getting PostData instance with id: " + id);
		try {
			PostData instance = (PostData) getCurrentSession().get(
					"com.xnx3.j2ee.entity.PostData", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PostData> findByExample(PostData instance) {
		log.debug("finding PostData instance by example");
		try {
			List<PostData> results = (List<PostData>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.PostData")
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
		log.debug("finding PostData instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PostData as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PostData> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List findAll() {
		log.debug("finding all PostData instances");
		try {
			String queryString = "from PostData";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PostData merge(PostData detachedInstance) {
		log.debug("merging PostData instance");
		try {
			PostData result = (PostData) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PostData instance) {
		log.debug("attaching dirty PostData instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PostData instance) {
		log.debug("attaching clean PostData instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PostDataDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PostDataDAO) ctx.getBean("PostDataDAO");
	}
}