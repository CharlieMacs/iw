package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.net.AliyunSMSUtil;

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
	
	/**
	 * 发送手机号登录的验证码
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单需提交参数：phone(发送到的手机号)
	 * @return {@link BaseVO}
	 */
	public BaseVO sendPhoneLoginCode(HttpServletRequest request);
	
	/**
	 * 向指定手机号发送指定内容的验证码，内容里六位动态验证码用${code}表示
	 * @param phone 发送至的手机号
	 * @param content 发送的包含验证码的内容
	 * @param type 发送类型，位于 {@link SmsLog}，以下几个数已使用,可从10以后开始用。此会计入 {@link SmsLog}.type数据字段
	 * 				<ul>
	 * 					<li>1:{@link SmsLog#TYPE_LOGIN}登录 </li>
	 * 					<li>2:{@link SmsLog#TYPE_FIND_PASSWORD}找回密码 </li>
	 * 					<li>3:{@link SmsLog#TYPE_BIND_PHONE}绑定手机 </li>
	 * 				</ul>
	 * @return {@link BaseVO}
	 */
	public BaseVO sendSms(HttpServletRequest request, String phone, String content, Short type);
	
	/**
	 * 输入手机号、动态验证码，验证是否成功
	 * @param phone 目标手机号
	 * @param code 六位数动态验证码
	 * @param type 发送类型，位于 {@link SmsLog}， {@link SmsLog}.type的值
	 * 				<ul>
	 * 					<li>1:{@link SmsLog#TYPE_LOGIN}登录 </li>
	 * 					<li>2:{@link SmsLog#TYPE_FIND_PASSWORD}找回密码 </li>
	 * 					<li>3:{@link SmsLog#TYPE_BIND_PHONE}绑定手机 </li>
	 * 				</ul>
	 * @return {@link BaseVO}
	 */
	public BaseVO verifyPhoneAndCode(String phone, String code, Short type);
	
	/**
	 * 使用阿里云短信通道，向指定手机号发送指定内容的验证码。
	 * <br/><b>注意，只支持一个变量，变量名为code，在设置模版的时候变量要用${code}</b>
	 * @param aliyunSMSUtil 项目中自行持久化的 {@link AliyunSMSUtil} 对象，主要拿它里面的 {@link AliyunSMSUtil#AliyunSMSUtil(String, String, String)} 初始化之后的参数信息regionId、accessKeyId、accessKeySecret
	 * @param signName 控制台创建的签名名称（状态必须是验证通过）
	 * 				<br/>&nbsp;&nbsp;&nbsp;&nbsp; https://sms.console.aliyun.com/?spm=#/sms/Sign
	 * @param templateCode 控制台创建的模板CODE（状态必须是验证通过）
	 * 				<br/>&nbsp;&nbsp;&nbsp;&nbsp; https://sms.console.aliyun.com/?spm=#/sms/Template
	 * @param phone 目标手机号，此处只支持单个手机号，若想用多个手机号，自行用 {@link AliyunSMSUtil#send(String, String, String, String)} 
	 * @param type 发送类型，位于 {@link SmsLog}，以下几个数已使用,可从10以后开始用。此会计入 {@link SmsLog}.type数据字段
	 * 				<ul>
	 * 					<li>1:{@link SmsLog#TYPE_LOGIN}登录 </li>
	 * 					<li>2:{@link SmsLog#TYPE_FIND_PASSWORD}找回密码 </li>
	 * 					<li>3:{@link SmsLog#TYPE_BIND_PHONE}绑定手机 </li>
	 * 				</ul>
	 * @return {@link BaseVO}
	 */
	public BaseVO sendByAliyunSMS(HttpServletRequest request, AliyunSMSUtil aliyunSMSUtil, String signName,String templateCode, String phone, Short type);
}