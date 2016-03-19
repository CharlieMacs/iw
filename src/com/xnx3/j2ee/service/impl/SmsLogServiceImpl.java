package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.SmsLogDAO;
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.service.SmsLogService;

@Service("smsLogService")
public class SmsLogServiceImpl implements SmsLogService {

	@Resource
	private SmsLogDAO smsLogDAO;
	
	@Override
	public void save(SmsLog transientInstance) {
		// TODO Auto-generated method stub
		smsLogDAO.save(transientInstance);
	}

	@Override
	public void delete(SmsLog persistentInstance) {
		// TODO Auto-generated method stub
		smsLogDAO.delete(persistentInstance);
	}

	@Override
	public SmsLog findById(Integer id) {
		// TODO Auto-generated method stub
		return smsLogDAO.findById(id);
	}

	@Override
	public List<SmsLog> findByExample(SmsLog instance) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<SmsLog> findByCode(Object code) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByCode(code);
	}

	@Override
	public List<SmsLog> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByUserid(userid);
	}

	@Override
	public List<SmsLog> findByUsed(Object used) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByUsed(used);
	}

	@Override
	public List<SmsLog> findByType(Object type) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByType(type);
	}

	@Override
	public List<SmsLog> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByAddtime(addtime);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return smsLogDAO.findAll();
	}

	@Override
	public SmsLog merge(SmsLog detachedInstance) {
		// TODO Auto-generated method stub
		return smsLogDAO.merge(detachedInstance);
	}

	@Override
	public List<SmsLog> findByPhone(Object phone) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByPhone(phone);
	}

	@Override
	public int findByPhoneNum(String phone,Short type) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByPhoneNum(phone, type);
	}

	@Override
	public List<SmsLog> findByIp(Object ip) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByIp(ip);
	}

	@Override
	public int findByIpNum(String ip,Short type) {
		// TODO Auto-generated method stub
		return smsLogDAO.findByIpNum(ip, type);
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
		return smsLogDAO.findByPhoneAddtimeUsedType(phone, addtime, used, type,code);
	}

}
