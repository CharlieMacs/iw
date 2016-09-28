package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.DateUtil;
import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.MessageDAO;
import com.xnx3.j2ee.dao.MessageDataDAO;
import com.xnx3.j2ee.dao.UserDAO;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.MessageData;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.MessageVO;

public class MessageServiceImpl implements MessageService {

	private MessageDAO messageDao;
	private MessageDataDAO messageDataDao;
	private LogDAO logDao;
	private UserDAO userDao;
	
	public MessageDAO getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}

	public MessageDataDAO getMessageDataDao() {
		return messageDataDao;
	}

	public void setMessageDataDao(MessageDataDAO messageDataDao) {
		this.messageDataDao = messageDataDao;
	}

	public LogDAO getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDAO logDao) {
		this.logDao = logDao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(Message transientInstance) {
		// TODO Auto-generated method stub
		messageDao.save(transientInstance);
	}

	@Override
	public void delete(Message persistentInstance) {
		// TODO Auto-generated method stub
		messageDao.delete(persistentInstance);
	}

	@Override
	public Message findById(Integer id) {
		// TODO Auto-generated method stub
		return messageDao.findById(id);
	}

	@Override
	public List<Message> findByExample(Message instance) {
		// TODO Auto-generated method stub
		return messageDao.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return messageDao.findByProperty(propertyName, value);
	}

	@Override
	public List<Message> findBySelf(Object self) {
		// TODO Auto-generated method stub
		return messageDao.findBySelf(self);
	}

	@Override
	public List<Message> findByOther(Object other) {
		// TODO Auto-generated method stub
		return messageDao.findByOther(other);
	}

	@Override
	public List<Message> findByTime(Object time) {
		// TODO Auto-generated method stub
		return messageDao.findByTime(time);
	}

	@Override
	public List<Message> findByState(Object state) {
		// TODO Auto-generated method stub
		return messageDao.findByState(state);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return messageDao.findAll();
	}

	public List findAllAndData(){
		return messageDao.findAllAndData();
	}

	@Override
	public BaseVO delete(int id) {
		BaseVO baseVO = new BaseVO();
		if(id>0){
			Message m = findById(id);
			if(m!=null){
				m.setIsdelete(Message.ISDELETE_DELETE);
				save(m);
				logDao.insert(m.getId(), "MESSAGE_DELETE");
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_notFind"));
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_idFailure"));
		}
		return baseVO;
	}

	@Override
	public BaseVO sendMessage(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		int recipientid = Lang.stringToInt(request.getParameter("recipientid"), 0);
		String content = request.getParameter("content");
		
		return sendMessage(recipientid, content);
	}

	@Override
	public MessageVO read(int id) {
		MessageVO messageVO = new MessageVO();
		if(id>0){
			Message message = findById(id);
			if(message!=null){
				int userId = ShiroFunc.getUser().getId();
				//查看此信息是此人发的，或者是发送给此人的，此人有权限查看
				if(userId==message.getRecipientid()||userId==message.getSenderid()){
					//检测此信息是否已被删除
					if(message.getIsdelete() == Message.ISDELETE_DELETE){
						messageVO.setBaseVO(MessageVO.FAILURE, Global.getLanguage("message_alreadyDelete"));
					}else{
						//拿到信息的内容
						MessageData messageData = messageDataDao.findById(id);
						messageVO.setContent(messageData.getContent());
						
						//如果阅读的人是收信人，且之前没有阅读过，则标注此信息为已阅读
						if(userId==message.getRecipientid()&&message.getState()==Message.MESSAGE_STATE_UNREAD){
							message.setState(Message.MESSAGE_STATE_READ);
							save(message);
							logDao.insert(message.getId(), "MESSAGE_READ", messageData.getContent());
						}
						messageVO.setMessage(message);
						
						//拿到发件人信息
						User senderUser = userDao.findById(message.getSenderid());
						
						//检验目标用户状态是否正常，是否被冻结
						if(senderUser.getIsfreeze() == User.ISFREEZE_FREEZE){
							messageVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_senderUserFreeze"));
							return messageVO;
						}
						messageVO.setSenderUser(senderUser);
						
						//拿到收件人信息
						User recipientUser = userDao.findById(message.getRecipientid());
						messageVO.setRecipientUser(recipientUser);
					}
				}else{
					messageVO.setBaseVO(MessageVO.FAILURE, Global.getLanguage("message_notRoleRead"));
				}
			}else{
				messageVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_notFind"));
			}
		}else{
			messageVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_idFailure"));
		}
		
		return messageVO;
	}

	@Override
	public BaseVO sendMessage(int recipientid, String content) {
		BaseVO baseVO = new BaseVO();
		User user = ShiroFunc.getUser();
		if(user == null){
			baseVO.setBaseVO(BaseVO.FAILURE, "请先登录");
			return baseVO;
		}
		return sendMessage(user.getId(), recipientid, content);
	}
	
	public BaseVO sendMessage(int userid, int recipientid, String content){
		BaseVO baseVO = new BaseVO();
		if(recipientid<1){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_unknowRecipient"));
			return baseVO;
		}
		
		if(recipientid == ShiroFunc.getUser().getId()){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_notSendOneself"));
			return baseVO;
		}
		
		if(content == null){
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_pleaseInputText"));
		}else if(content.length()>Global.MESSAGE_CONTENT_MINLENGTH&&content.length()<Global.MESSAGE_CONTENT_MAXLENGTH) {
			//正常
			
			//拿到收信人信息
			User recipiendUser = userDao.findById(recipientid);
			if(recipiendUser == null){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_sendRecipientNotFind"));
				return baseVO;
			}
			//检验目标用户状态是否正常，是否被冻结
			if(recipiendUser.getIsfreeze() == User.ISFREEZE_FREEZE){
				baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_sendRecipientUserFreeze"));
				return baseVO;
			}
			
			Message message = new Message();
			message.setSenderid(ShiroFunc.getUser().getId());
			message.setRecipientid(recipientid);
			message.setTime(DateUtil.timeForUnix10());
			message.setState(Message.MESSAGE_STATE_UNREAD);
			message.setIsdelete(Message.ISDELETE_NORMAL);
			save(message);
			baseVO.setBaseVO(BaseVO.SUCCESS, message.getId()+"");
			
			MessageData messageData = new MessageData();
			messageData.setId(message.getId());
			messageData.setContent(content);
			messageDataDao.save(messageData);
			
			if(messageData.getId()==0){
				baseVO.setBaseVO(BaseVO.FAILURE,Global.getLanguage("message_saveFailure"));
			}else{
				logDao.insert(message.getId(), "MESSAGE_SEND",content);
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Global.getLanguage("message_sizeFailure").replaceAll("\\$\\{min\\}", Global.MESSAGE_CONTENT_MINLENGTH+"").replaceAll("\\$\\{max\\}", Global.MESSAGE_CONTENT_MAXLENGTH+""));
		}
		
		return baseVO;
	}

	@Override
	public BaseVO sendSystemMessage(int recipientid, String content) {
		return sendMessage(0, recipientid, content);
	}
}
