package com.xnx3.j2ee.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.xnx3.j2ee.entity.PostComment;

/**
 * A data access object (DAO) providing persistence and search support for
 * PostComment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.PostComment
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PostCommentDAO {
	private static final Logger log = LoggerFactory
			.getLogger(PostCommentDAO.class);
	// property constants
	public static final String POSTID = "postid";
	public static final String ADDTIME = "addtime";
	public static final String USERID = "userid";
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

	public void save(PostComment transientInstance) {
		log.debug("saving PostComment instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PostComment persistentInstance) {
		log.debug("deleting PostComment instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PostComment findById(java.lang.Integer id) {
		log.debug("getting PostComment instance with id: " + id);
		try {
			PostComment instance = (PostComment) getCurrentSession().get(
					"com.xnx3.j2ee.entity.PostComment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PostComment> findByExample(PostComment instance) {
		log.debug("finding PostComment instance by example");
		try {
			List<PostComment> results = (List<PostComment>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.PostComment")
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
		log.debug("finding PostComment instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PostComment as model where model."
					+ propertyName + "= ? ORDER BY id DESC";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PostComment> findByPostid(Object postid) {
		return findByProperty(POSTID, postid);
	}

	public List<PostComment> findByAddtime(Object addtime) {
		return findByProperty(ADDTIME, addtime);
	}

	public List<PostComment> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<PostComment> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List findAll() {
		log.debug("finding all PostComment instances");
		try {
			String queryString = "from PostComment";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PostComment merge(PostComment detachedInstance) {
		log.debug("merging PostComment instance");
		try {
			PostComment result = (PostComment) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PostComment instance) {
		log.debug("attaching dirty PostComment instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PostComment instance) {
		log.debug("attaching clean PostComment instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PostCommentDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PostCommentDAO) ctx.getBean("PostCommentDAO");
	}
	
	/**
	 * 根据帖子id查所有回帖，排序为 id DESC
	 * @param postid 帖子id
	 * @return List<comment.addtime,comment.userid,comment.text,user.head,user.nickname,user.id>
	 */
	public List commentAndUser(int postid){
		return commentAndUser(postid, 0);
	}
	
	/**
	 * 根据帖子id查回帖，排序为 id DESC
	 * @param postid 帖子id
	 * @param limit 条数，若为0则显示所有
	 * @return List<comment.addtime,comment.userid,comment.text,user.head,user.nickname,user.id>
	 */
	public List commentAndUser(int postid,int limit){
		String limitString="";
		if(limit > 0){
			limitString = " LIMIT 0,"+limit;
		}
		try {
			String queryString = "SELECT comment.addtime,comment.userid,comment.text,user.head,user.nickname,user.id FROM post_comment comment,user WHERE comment.userid=user.id AND comment.postid= ? ORDER BY comment.id DESC "+limitString;
			Query queryObject = getCurrentSession().createSQLQuery(queryString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
			queryObject.setParameter(0, postid);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据帖子id查回帖的数量
	 * @param postid 帖子id
	 * @return 回帖数量
	 */
	public int count(int postid){
		try {
			String queryString = "SELECT count(id) FROM post_comment WHERE postid= ? ";
			Query queryObject = getCurrentSession().createSQLQuery(queryString);
			queryObject.setParameter(0, postid);
			BigInteger b = (BigInteger) queryObject.uniqueResult();
			return b.intValue(); 
		} catch (RuntimeException re) {
			log.error("find by count name failed", re);
			throw re;
		}
	}
}