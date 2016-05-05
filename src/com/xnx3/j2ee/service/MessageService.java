package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.vo.BaseVO;

/**
 * 站内信
 * @author 管雷鸣
 *
 */
public interface MessageService {

	public void save(Message transientInstance);

	public void delete(Message persistentInstance);

	public Message findById(java.lang.Integer id);

	public List<Message> findByExample(Message instance);

	public List findByProperty(String propertyName, Object value);

	public List<Message> findBySelf(Object self);

	public List<Message> findByOther(Object other);

	public List<Message> findByTime(Object time);

	public List<Message> findByState(Object state);

	public List findAll();

	public List findAllAndData();
	
	/**
	 * 删除信息，逻辑删除，改变state=2
	 * @param id 要删除信息的message.id
	 * @return {@link BaseVO}
	 */
	public BaseVO delete(int id);
	
	/**
	 * 发送信息
	 * @param request {@link HttpServletRequest}
	 * 		<br/>form表单进行发送信息提交时，需传递两个参数：接收者用户id(otherid)，信息内容(content)
	 * @return {@link BaseVO}
	 */
	public BaseVO sendMessage(HttpServletRequest request);
}