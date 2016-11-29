package com.xnx3.j2ee.service.impl;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.xnx3.DateUtil;
import com.xnx3.SendPhoneMsgUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.SmsLogDAO;
import com.xnx3.j2ee.entity.SmsLog;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.SmsLogService;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.net.SMSUtil;

public class SmsLogServiceImpl implements SmsLogService {

	private SmsLogDAO smsLogDAO;
	public SmsLogDAO getSmsLogDAO() {
		return smsLogDAO;
	}
	public void setSmsLogDAO(SmsLogDAO smsLogDAO) {
		this.smsLogDAO = smsLogDAO;
	}

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

	/**
	 * 发送手机号登录的验证码
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单需提交参数：phone(发送到的手机号)
	 * @return {@link BaseVO}
	 */
	@Override
	public BaseVO sendPhoneLoginCode(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String phone = request.getParameter("phone");
		if(phone==null || phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_sendPhoneLoginCodeNumberFailure"));
		}else{
			//此参数决定最终是否可发送短信验证码
			boolean send = false;
			
			//查询当前手机号是否已发送满条件
			if(SmsLog.everyDayPhoneNum > 0){
				int phoneNum = findByPhoneNum(phone, SmsLog.TYPE_LOGIN);
				if(phoneNum<SmsLog.everyDayPhoneNum){
					send = true;
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_thisPhoneNumberDayUpperLimit"));
					send = false;
				}
			}else{
				send = true;
			}
			
			if(send){
				if(SmsLog.everyDayIpNum > 0){
					int ipNum = findByIpNum(IpUtil.getIpAddress(request), SmsLog.TYPE_LOGIN);
					if(ipNum<SmsLog.everyDayIpNum){
						send = true;
					}else{
						baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_thisIpDayUpperLimit"));
						send = false;
					}
				}
			}
			
			//发送验证码流程逻辑
			if(send){
				Random random = new Random();
				String code = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
				
				SmsLog smsLog = new SmsLog();
				smsLog.setAddtime(DateUtil.timeForUnix10());
				smsLog.setCode(code);
				smsLog.setIp(IpUtil.getIpAddress(request));
				smsLog.setPhone(phone);
				smsLog.setType(SmsLog.TYPE_LOGIN);
				smsLog.setUsed(SmsLog.USED_FALSE);
				smsLog.setUserid(0);
				save(smsLog);
				
				if(smsLog.getId()>0){
					SMSUtil.send(phone, Global.getLanguage("sms_loginSendCodeText").replaceAll("\\$\\{code\\}", code+""));
					baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("sms_codeSendYourPhoneSuccess"));
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_saveFailure"));
				}
			}
		}
		
		return baseVO;
	}

	@Override
	public BaseVO sendSms(HttpServletRequest request, String phone, String content, Short type) {
		BaseVO baseVO = new BaseVO();
		if(phone==null || phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_sendSmsPhoneNumberFailure"));
		}else{
			//此参数决定最终是否可发送短信验证码
			boolean send = false;
			
			//查询当前手机号是否已发送满条件
			if(SmsLog.everyDayPhoneNum > 0){
				int phoneNum = findByPhoneNum(phone, type);
				if(phoneNum<SmsLog.everyDayPhoneNum){
					send = true;
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_thisPhoneNumberDayUpperLimit"));
					send = false;
				}
			}else{
				send = true;
			}
			
			if(send){
				if(SmsLog.everyDayIpNum > 0){
					int ipNum = findByIpNum(IpUtil.getIpAddress(request), type);
					if(ipNum<SmsLog.everyDayIpNum){
						send = true;
					}else{
						baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_thisIpDayUpperLimit"));
						send = false;
					}
				}
			}
			
			//发送验证码流程逻辑
			if(send){
				Random random = new Random();
				String code = random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
				
				SmsLog smsLog = new SmsLog();
				smsLog.setAddtime(DateUtil.timeForUnix10());
				smsLog.setCode(code);
				smsLog.setIp(IpUtil.getIpAddress(request));
				smsLog.setPhone(phone);
				smsLog.setType(type);
				smsLog.setUsed(SmsLog.USED_FALSE);
				smsLog.setUserid(0);
				save(smsLog);
				
				if(smsLog.getId()>0){
					String result = SMSUtil.send(phone, content.replaceAll("\\$\\{code\\}", code+""));
					if(result == null){
						baseVO.setBaseVO(BaseVO.SUCCESS, Global.getLanguage("sms_codeSendYourPhoneSuccess"));
					}else{
						baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_saveFailure")+"-"+result);
					}
				}else{
					baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_saveFailure"));
				}
			}
		}
		
		return baseVO;
	}

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
	@Override
	public BaseVO verifyPhoneAndCode(String phone, String code, Short type) {
		BaseVO baseVO = new BaseVO();
		if(phone==null || phone.length() != 11){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("sms_sendSmsPhoneNumberFailure"));
			return baseVO;
		}
		if(code==null || code.length() != 6){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodeCodeFailure"));
			return baseVO;
		}
		
    	List<SmsLog> smsLogList;
    	if(SmsLog.codeValidity == 0){
    		SmsLog smsLogSearch = new SmsLog();
        	smsLogSearch.setType(type);
        	smsLogSearch.setUsed(SmsLog.USED_FALSE);
        	smsLogSearch.setCode(code);
    		smsLogList = smsLogDAO.findByExample(smsLogSearch);
    	}else{
    		int currentTime = DateUtil.timeForUnix10();
    		smsLogList = smsLogDAO.findByPhoneAddtimeUsedType(phone, currentTime-SmsLog.codeValidity, SmsLog.USED_FALSE, type,code);
    	}
    	if(smsLogList.size()>0){
    		SmsLog smsLog = smsLogList.get(0);
    		
    		/****更改SmsLog状态*****/
    		smsLog.setUserid(0);
    		smsLog.setUsed(SmsLog.USED_TRUE);
    		smsLogDAO.save(smsLog);
    		
			baseVO.setResult(BaseVO.SUCCESS);
			return baseVO;
    	}else{
    		baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("user_loginByPhoneAndCodeCodeNotFind"));
    		return baseVO;
    	}
	}
	
}
