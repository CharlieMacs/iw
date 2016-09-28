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
import com.xnx3.j2ee.entity.Permission;
import com.xnx3.j2ee.entity.RolePermission;

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
public class RolePermissionDAO {
	private static final Logger log = LoggerFactory.getLogger(RolePermissionDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String ROLEID = "roleid";
	public static final String PERMISSIONID = "permissionid";

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

	public void save(RolePermission transientInstance) {
		log.debug("saving RolePermission instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RolePermission persistentInstance) {
		log.debug("deleting RolePermission instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RolePermission findById(java.lang.Integer id) {
		log.debug("getting RolePermission instance with id: " + id);
		try {
			RolePermission instance = (RolePermission) getCurrentSession().get(
					"com.xnx3.j2ee.entity.RolePermission", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RolePermission instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RolePermission as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<RolePermission> findByRoleId(Object roleId) {
		return findByProperty(ROLEID, roleId);
	}
	
	public List<RolePermission> findByPermissionId(Object permissionId) {
		return findByProperty(PERMISSIONID, permissionId);
	}
	
	/**
	 * 根据用户id，得到用户有哪些角色
	 * @param userId
	 * @return
	 */
	public List<Permission> findPermissionByRoleId(Integer roleId) {
		try {
			String queryString = "SELECT permission.* FROM permission,role_permission WHERE role_permission.permissionid=permission.id AND role_permission.roleid=?0 ";
			Query queryObject = getCurrentSession().createSQLQuery(queryString).addEntity(Permission.class);
			queryObject.setParameter("0", roleId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	
	public List<RolePermission> findByExample(RolePermission instance) {
		log.debug("finding RolePermission instance by example");
		try {
			List<RolePermission> results = (List<RolePermission>) getCurrentSession()
					.createCriteria("com.xnx3.j2ee.entity.RolePermission")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	public List findAll() {
		log.debug("finding all RolePermission instances");
		try {
			String queryString = "from RolePermission";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public static RolePermissionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RolePermissionDAO) ctx.getBean("RolePermissionDAO");
	}
}