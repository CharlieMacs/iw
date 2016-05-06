package com.xnx3.j2ee.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.MessageVO;

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
	 * 删除信息，逻辑删除，改变isdelete=1
	 * @param id 要删除信息的message.id
	 * @return {@link BaseVO}
	 */
	public BaseVO delete(int id);
	
	/**
	 * 发送信息
	 * @param request {@link HttpServletRequest}
	 * 		<br/>form表单进行发送信息提交时，需传递两个参数：recipientid(接收者用户id)，content(信息内容)
	 * @return {@link BaseVO}
	 */
	public BaseVO sendMessage(HttpServletRequest request);
	
	/**
	 * 通过 {@link Message}.id获取此条站内信的具体信息，包含 {@link Message}、 {@link MessageData}
	 * @param id {@link Message}.id
	 * @return {@link MessageVO}
	 * 		<br/>首先判断getResult()是否是 {@link BaseVO#SUCCESS}，若是，才可以调取其他的值。若不是，可通过getInfo()获取错误信息
	 */
	public MessageVO findMessageVOById(int id);
}