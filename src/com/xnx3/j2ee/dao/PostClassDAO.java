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
import com.xnx3.j2ee.entity.PostClass;

/**
 * A data access object (DAO) providing persistence and search support for
 * PostClass entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.PostClass
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PostClassDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PostClassDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ISDELETE = "isdelete";

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

	public void save(PostClass transientInstance) {
		log.debug("saving PostClass instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PostClass persistentInstance) {
		log.debug("deleting PostClass instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PostClass findById(java.lang.Integer id) {
		log.debug("getting PostClass instance with id: " + id);
		try {
			PostClass instance = (PostClass) getCurrentSession().get(
					"com.xnx3.j2ee.entity.PostClass", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PostClass> findByExample(PostClass instance) {
		log.debug("finding PostClass instance by example");
		try {
			List<PostClass> results = (List<PostClass>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.PostClass")
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
		log.debug("finding PostClass instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PostClass as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PostClass> findByName(Object name) {
		return findByProperty(NAME, name);
	}
	
	public List findAll() {
		log.debug("finding all PostClass instances");
		try {
			String queryString = "from PostClass";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findByIsdelete(Short isdelete) {
		log.debug("finding all PostClass instances");
		try {
			String queryString = "from PostClass where isdelete = "+isdelete;
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public PostClass merge(PostClass detachedInstance) {
		log.debug("merging PostClass instance");
		try {
			PostClass result = (PostClass) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PostClass instance) {
		log.debug("attaching dirty PostClass instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PostClass instance) {
		log.debug("attaching clean PostClass instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PostClassDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PostClassDAO) ctx.getBean("PostClassDAO");
	}
}