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
import com.xnx3.j2ee.entity.Post;

/**
 * A data access object (DAO) providing persistence and search support for Post
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.Post
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PostDAO {
	private static final Logger log = LoggerFactory.getLogger(PostDAO.class);
	// property constants
	public static final String CLASSID = "classid";
	public static final String TITLE = "title";
	public static final String VIEW = "view";
	public static final String INFO = "info";
	public static final String ADDTIME = "addtime";
	public static final String USERID = "userid";
	public static final String STATE = "state";

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

	public void save(Post transientInstance) {
		log.debug("saving Post instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Post persistentInstance) {
		log.debug("deleting Post instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Post findById(java.lang.Integer id) {
		log.debug("getting Post instance with id: " + id);
		try {
			Post instance = (Post) getCurrentSession().get(
					"com.xnx3.j2ee.entity.Post", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Post> findByExample(Post instance) {
		log.debug("finding Post instance by example");
		try {
			List<Post> results = (List<Post>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.Post")
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
		log.debug("finding Post instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Post as model where model."
					+ propertyName + "= ? ORDER BY id DESC";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Post> findByClassid(Object classid) {
		return findByProperty(CLASSID, classid);
	}

	public List<Post> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<Post> findByView(Object view) {
		return findByProperty(VIEW, view);
	}

	public List<Post> findByInfo(Object info) {
		return findByProperty(INFO, info);
	}

	public List<Post> findByAddtime(Object addtime) {
		return findByProperty(ADDTIME, addtime);
	}
	
	public List<Post> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<Post> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findAll() {
		log.debug("finding all Post instances");
		try {
			String queryString = "from Post";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Post merge(Post detachedInstance) {
		log.debug("merging Post instance");
		try {
			Post result = (Post) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Post instance) {
		log.debug("attaching dirty Post instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Post instance) {
		log.debug("attaching clean Post instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PostDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PostDAO) ctx.getBean("PostDAO");
	}
	
}