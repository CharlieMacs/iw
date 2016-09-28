package com.xnx3.j2ee.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.j2ee.entity.SmsLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * SmsLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.xnx3.j2ee.entity.SmsLog
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class SmsLogDAO {
	private static final Logger log = LoggerFactory.getLogger(SmsLogDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String USERID = "userid";
	public static final String USED = "used";
	public static final String TYPE = "type";
	public static final String ADDTIME = "addtime";
	public static final String PHONE = "phone";
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

	public void save(SmsLog transientInstance) {
		log.debug("saving SmsLog instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SmsLog persistentInstance) {
		log.debug("deleting SmsLog instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SmsLog findById(java.lang.Integer id) {
		log.debug("getting SmsLog instance with id: " + id);
		try {
			SmsLog instance = (SmsLog) getCurrentSession().get(
					"com.xnx3.j2ee.entity.SmsLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SmsLog> findByExample(SmsLog instance) {
		log.debug("finding SmsLog instance by example");
		try {
			List<SmsLog> results = (List<SmsLog>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.SmsLog")
					.addOrder(Order.desc("id"))
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
		log.debug("finding SmsLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SmsLog as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SmsLog> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<SmsLog> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<SmsLog> findByUsed(Object used) {
		return findByProperty(USED, used);
	}

	public List<SmsLog> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<SmsLog> findByAddtime(Object addtime) {
		return findByProperty(ADDTIME, addtime);
	}
	
	public List<SmsLog> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}
	
	public List<SmsLog> findByIp(Object ip) {
		return findByProperty(IP,ip);
	}

	public List findAll() {
		log.debug("finding all SmsLog instances");
		try {
			String queryString = "from SmsLog";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SmsLog merge(SmsLog detachedInstance) {
		log.debug("merging SmsLog instance");
		try {
			SmsLog result = (SmsLog) getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SmsLog instance) {
		log.debug("attaching dirty SmsLog instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SmsLog instance) {
		log.debug("attaching clean SmsLog instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 获取当前条件下的这个手机号，当天信息记录有多少
	 * @param ip 发送者ip
	 * @param type 类型，如{@link SmsLog#TYPE_LOGIN}
	 * @return 记录数
	 */
	public int findByPhoneNum(String phone,Short type){
		int weeHours = DateUtil.dateToInt10(DateUtil.weeHours(new Date()));
		try {
			String queryString = "SELECT count(id) FROM sms_log WHERE phone = '"+phone+"' AND addtime > "+weeHours + " AND type = "+type;
			Object count = getCurrentSession().createSQLQuery(queryString).uniqueResult();
			return Lang.stringToInt(count+"", 0);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 获取当前条件下的IP，当天信息记录有多少
	 * @param ip 发送者ip
	 * @param type 类型，如{@link SmsLog#TYPE_LOGIN}
	 * @return 记录数
	 */
	public int findByIpNum(String ip,Short type){
		int weeHours = DateUtil.dateToInt10(DateUtil.weeHours(new Date()));
		try {
			String queryString = "SELECT count(id) FROM sms_log WHERE ip = '"+ip+"' AND addtime > "+weeHours + " AND type = "+type;
			Object count = getCurrentSession().createSQLQuery(queryString).uniqueResult();
			return Lang.stringToInt(count+"", 0);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 根据手机号、是否使用，类型，以及发送时间，查询符合的数据列表
	 * @param phone 手机号
	 * @param addtime 添加使用，即发送时间，查询数据的时间大于此时间
	 * @param used 是否使用，如 {@link SmsLog#USED_FALSE}
	 * @param type 短信验证码类型，如 {@link SmsLog#TYPE_LOGIN}
	 * @param code 短信验证码
	 * @return
	 */
	public List findByPhoneAddtimeUsedType(String phone,int addtime,Short used,Short type,String code){
		try {
			String queryString = "from SmsLog as model where model.phone= :phone and model.addtime > :addtime and model.used = :used and model.type = :type and model.code = :code";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("phone", phone);
			queryObject.setParameter("addtime", addtime);
			queryObject.setParameter("used", used);
			queryObject.setParameter("type", type);
			queryObject.setParameter("code", code);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public static SmsLogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SmsLogDAO) ctx.getBean("SmsLogDAO");
	}
}