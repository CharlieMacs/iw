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
import com.xnx3.j2ee.entity.MessageData;

/**
 * A data access object (DAO) providing persistence and search support for
 * MessageData entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.MessageData
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class MessageDataDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MessageDataDAO.class);
	// property constants
	public static final String CONTENT = "content";

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

	public void save(MessageData transientInstance) {
		log.debug("saving MessageData instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MessageData persistentInstance) {
		log.debug("deleting MessageData instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MessageData findById(java.lang.Integer id) {
		log.debug("getting MessageData instance with id: " + id);
		try {
			MessageData instance = (MessageData) getCurrentSession().get(
					"com.xnx3.j2ee.entity.MessageData", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<MessageData> findByExample(MessageData instance) {
		log.debug("finding MessageData instance by example");
		try {
			List<MessageData> results = (List<MessageData>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.MessageData")
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
		log.debug("finding MessageData instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MessageData as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<MessageData> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all MessageData instances");
		try {
			String queryString = "from MessageData";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MessageData merge(MessageData detachedInstance) {
		log.debug("merging MessageData instance");
		try {
			MessageData result = (MessageData) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MessageData instance) {
		log.debug("attaching dirty MessageData instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MessageData instance) {
		log.debug("attaching clean MessageData instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MessageDataDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MessageDataDAO) ctx.getBean("MessageDataDAO");
	}
}