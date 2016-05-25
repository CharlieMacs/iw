package com.xnx3.j2ee.service.impl;

import java.util.List;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.dao.CollectDAO;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.entity.Collect;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.CollectService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;

public class CollectServiceImpl implements CollectService {
	
	private CollectDAO collectDAO;
	private LogDAO logDAO;
	private UserDAO userDAO;
	
	public CollectDAO getCollectDAO() {
		return collectDAO;
	}

	public void setCollectDAO(CollectDAO collectDAO) {
		this.collectDAO = collectDAO;
	}

	public LogDAO getLogDAO() {
		return logDAO;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void save(Collect transientInstance) {
		// TODO Auto-generated method stub
		collectDAO.save(transientInstance);
	}

	@Override
	public void delete(Collect persistentInstance) {
		// TODO Auto-generated method stub
		collectDAO.delete(persistentInstance);
	}

	@Override
	public Collect findById(Integer id) {
		// TODO Auto-generated method stub
		return collectDAO.findById(id);
	}

	@Override
	public List<Collect> findByExample(Collect instance) {
		// TODO Auto-generated method stub
		return collectDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return collectDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Collect> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return collectDAO.findByUserid(userid);
	}

	@Override
	public List<Collect> findByOthersid(Object othersid) {
		// TODO Auto-generated method stub
		return collectDAO.findByOthersid(othersid);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return collectDAO.findAll();
	}
	
	/**
	 * 增加关注
	 * @param userid 要关注的用户的id
	 * @return {@link BaseVO} 若成功，info返回关注成功的 collect.id
	 */
	public BaseVO addCollect(int userid){
		BaseVO baseVO = new BaseVO();
		
		User user = userDAO.findById(userid);
		if(user == null){
			baseVO.setBaseVO(BaseVO.FAILURE, "要关注的用户不存在！");
			return baseVO;
		}
		
		Collect collect = new Collect();
		collect.setOthersid(userid);
		collect.setUserid(ShiroFunc.getUser().getId());
		List<Collect> list = findByExample(collect);;
		if(list.size()>0){
			baseVO.setBaseVO(BaseVO.FAILURE, "已经关注过了，无需再关注！");
			return baseVO;
		}
		
		collect.setAddtime(DateUtil.timeForUnix10());
		save(collect);
		if(collect.getId()>0){
			logDAO.insert(collect.getId(), "COLLECT_ADD");
			baseVO.setBaseVO(BaseVO.SUCCESS, collect.getId()+"");
			return baseVO;
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "存入关注表失败！");
			return baseVO;
		}
	}

	@Override
	public BaseVO cancelCollect(int userid) {
		BaseVO baseVO = new BaseVO();
		
		Collect collect = new Collect();
		collect.setOthersid(userid);
		collect.setUserid(ShiroFunc.getUser().getId());
		List<Collect> list = findByExample(collect);
		if(list.size()==0){
			baseVO.setBaseVO(BaseVO.FAILURE, "您并未关注此人，不需要取消关注");
			return baseVO;
		}
		Collect c = list.get(0);
		delete(c);
		logDAO.insert(c.getOthersid(), "COLLECT_DELETE");
		
		return baseVO;
	}
	
	
}
