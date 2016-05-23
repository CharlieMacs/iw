package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.PayLog;

/**
 * 支付日志记录
 * @author 管雷鸣
 */
public interface PayLogService {

	public abstract void save(PayLog transientInstance);

	public abstract void delete(PayLog persistentInstance);

	public abstract PayLog findById(java.lang.Integer id);

	public abstract List<PayLog> findByExample(PayLog instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<PayLog> findByChannel(Object channel);

	public abstract List<PayLog> findByAddtime(Object addtime);

	public abstract List<PayLog> findByMoney(Object money);

	public abstract List<PayLog> findByOrderno(Object orderno);

	public abstract List findAll();

}