package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.MessageDAO;
import com.xnx3.j2ee.dao.MessageDataDAO;
import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.MessageData;
import com.xnx3.j2ee.service.MessageService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageDAO messageDao;
	@Resource
	private MessageDataDAO messageDataDao;
	@Resource
	private LogDAO logDao;
	
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
				baseVO.setBaseVO(BaseVO.FAILURE, "信息不存在");
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "信息编号传入错误");
		}
		return baseVO;
	}

	@Override
	public BaseVO sendMessage(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		String otheridString = request.getParameter("otherid");
		String content = request.getParameter("content");
		if(otheridString == null || otheridString.length()==0){
			baseVO.setBaseVO(BaseVO.FAILURE, "信息发送给谁呢？");
			return baseVO;
		}
		int otherId = Integer.parseInt(otheridString);
		if(otherId<1){
			baseVO.setBaseVO(BaseVO.FAILURE, "信息发送给谁呢？");
			return baseVO;
		}
		
		if(content == null){
			baseVO.setBaseVO(BaseVO.FAILURE, "请输入要发送的信息内容");
		}else if(content.length()>Global.MESSAGE_CONTENT_MINLENGTH&&content.length()<Global.MESSAGE_CONTENT_MAXLENGTH) {
			//正常
			Message message = new Message();
			message.setSelf(ShiroFunc.getUser().getId());
			message.setOther(otherId);
			message.setTime(DateUtil.timeForUnix10());
			message.setState(Message.MESSAGE_STATE_UNREAD);
			message.setIsdelete(Message.ISDELETE_NORMAL);
			save(message);
			
			MessageData messageData = new MessageData();
			messageData.setId(message.getId());
			messageData.setContent(content);
			messageDataDao.save(messageData);
			
			if(messageData.getId()==0){
				baseVO.setBaseVO(BaseVO.FAILURE,"信息发送失败");
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "信息内容必须在"+Global.MESSAGE_CONTENT_MINLENGTH+"～"+Global.MESSAGE_CONTENT_MAXLENGTH+"之间");
		}
		
		return baseVO;
	}
}
