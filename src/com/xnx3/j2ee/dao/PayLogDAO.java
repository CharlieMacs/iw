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

import com.xnx3.j2ee.entity.PayLog;

/**
 	* A data access object (DAO) providing persistence and search support for PayLog entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.qdsaizhuo.tongchengbang.dao.PayLog
  * @author MyEclipse Persistence Tools 
 */
    @Transactional   
public class PayLogDAO  {
	     private static final Logger log = LoggerFactory.getLogger(PayLogDAO.class);
		//property constants
	public static final String CHANNEL = "channel";
	public static final String ADDTIME = "addtime";
	public static final String MONEY = "money";
	public static final String ORDERNO = "orderno";



    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession(){
     return sessionFactory.getCurrentSession(); 
    }
	protected void initDao() {
		//do nothing
	}
    
    public void save(PayLog transientInstance) {
        log.debug("saving PayLog instance");
        try {
            getCurrentSession().saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(PayLog persistentInstance) {
        log.debug("deleting PayLog instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public PayLog findById( java.lang.Integer id) {
        log.debug("getting PayLog instance with id: " + id);
        try {
            PayLog instance = (PayLog) getCurrentSession()
                    .get("com.qdsaizhuo.tongchengbang.dao.PayLog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<PayLog> findByExample(PayLog instance) {
        log.debug("finding PayLog instance by example");
        try {
            List<PayLog> results = (List<PayLog>) getCurrentSession() .createCriteria("com.qdsaizhuo.tongchengbang.dao.PayLog").add( create(instance) ).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding PayLog instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from PayLog as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getCurrentSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List<PayLog> findByChannel(Object channel
	) {
		return findByProperty(CHANNEL, channel
		);
	}
	
	public List<PayLog> findByAddtime(Object addtime
	) {
		return findByProperty(ADDTIME, addtime
		);
	}
	
	public List<PayLog> findByMoney(Object money
	) {
		return findByProperty(MONEY, money
		);
	}
	
	public List<PayLog> findByOrderno(Object orderno
	) {
		return findByProperty(ORDERNO, orderno
		);
	}
	

	public List findAll() {
		log.debug("finding all PayLog instances");
		try {
			String queryString = "from PayLog";
	         Query queryObject = getCurrentSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public PayLog merge(PayLog detachedInstance) {
        log.debug("merging PayLog instance");
        try {
            PayLog result = (PayLog) getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PayLog instance) {
        log.debug("attaching dirty PayLog instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(PayLog instance) {
        log.debug("attaching clean PayLog instance");
        try {
                      	getCurrentSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static PayLogDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (PayLogDAO) ctx.getBean("PayLogDAO");
	}
}