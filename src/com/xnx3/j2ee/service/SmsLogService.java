package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.SmsLog;

public interface SmsLogService {

	public void save(SmsLog transientInstance);

	public void delete(SmsLog persistentInstance);

	public SmsLog findById(java.lang.Integer id);

	public List<SmsLog> findByExample(SmsLog instance);

	public List findByProperty(String propertyName, Object value);

	public List<SmsLog> findByCode(Object code);

	public List<SmsLog> findByUserid(Object userid);

	public List<SmsLog> findByUsed(Object used);

	public List<SmsLog> findByType(Object type);

	public List<SmsLog> findByAddtime(Object addtime);

	public List findAll();

	public SmsLog merge(SmsLog detachedInstance);
	
	public List<SmsLog> findByPhone(Object phone);
	
	/**
	 * 获取当前条件下的这个手机号，当天信息记录有多少
	 * @param ip 发送者ip
	 * @param type 类型，如{@link SmsLog#TYPE_LOGIN}
	 * @return 记录数
	 */
	public int findByPhoneNum(String phone,Short type);
	
	public List<SmsLog> findByIp(Object ip);
	
	/**
	 * 获取当前条件下的IP，当天信息记录有多少
	 * @param ip 发送者ip
	 * @param type 类型，如{@link SmsLog#TYPE_LOGIN}
	 * @return 记录数
	 */
	public int findByIpNum(String ip,Short type);
	
	/**
	 * 根据手机号、是否使用，类型，以及发送时间，查询符合的数据列表
	 * @param phone 手机号
	 * @param addtime 添加使用，即发送时间，查询数据的时间大于此时间
	 * @param used 是否使用，如 {@link SmsLog#USED_FALSE}
	 * @param type 短信验证码类型，如 {@link SmsLog#TYPE_LOGIN}
	 * @param code 验证码
	 * @return
	 */
	public List findByPhoneAddtimeUsedType(String phone,int addtime,Short used,Short type,String code);
}