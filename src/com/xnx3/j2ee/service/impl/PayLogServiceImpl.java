package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.PayLogDAO;
import com.xnx3.j2ee.entity.PayLog;
import com.xnx3.j2ee.service.PayLogService;

public class PayLogServiceImpl implements PayLogService {

	private PayLogDAO payLogDAO;
	public PayLogDAO getPayLogDAO() {
		return payLogDAO;
	}

	public void setPayLogDAO(PayLogDAO payLogDAO) {
		this.payLogDAO = payLogDAO;
	}

	@Override
	public void save(PayLog transientInstance) {
		// TODO Auto-generated method stub
		payLogDAO.save(transientInstance);
	}

	@Override
	public void delete(PayLog persistentInstance) {
		// TODO Auto-generated method stub
		payLogDAO.delete(persistentInstance);
	}

	@Override
	public PayLog findById(Integer id) {
		// TODO Auto-generated method stub
		return payLogDAO.findById(id);
	}

	@Override
	public List<PayLog> findByExample(PayLog instance) {
		// TODO Auto-generated method stub
		return payLogDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return payLogDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<PayLog> findByChannel(Object channel) {
		// TODO Auto-generated method stub
		return payLogDAO.findByChannel(channel);
	}

	@Override
	public List<PayLog> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return payLogDAO.findByAddtime(addtime);
	}

	@Override
	public List<PayLog> findByMoney(Object money) {
		// TODO Auto-generated method stub
		return payLogDAO.findByMoney(money);
	}

	@Override
	public List<PayLog> findByOrderno(Object orderno) {
		// TODO Auto-generated method stub
		return payLogDAO.findByOrderno(orderno);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return payLogDAO.findAll();
	}

}
