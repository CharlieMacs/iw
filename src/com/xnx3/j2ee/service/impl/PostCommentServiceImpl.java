package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.PostCommentDAO;
import com.xnx3.j2ee.entity.PostComment;
import com.xnx3.j2ee.service.PostCommentService;

@Service("postCommentService")
public class PostCommentServiceImpl implements PostCommentService {

	@Resource
	private PostCommentDAO postCommentDAO;
	
	@Override
	public void save(PostComment transientInstance) {
		// TODO Auto-generated method stub
		postCommentDAO.save(transientInstance);
	}

	@Override
	public void delete(PostComment persistentInstance) {
		// TODO Auto-generated method stub
		postCommentDAO.delete(persistentInstance);
	}

	@Override
	public PostComment findById(Integer id) {
		// TODO Auto-generated method stub
		return postCommentDAO.findById(id);
	}

	@Override
	public List<PostComment> findByExample(PostComment instance) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<PostComment> findByPostid(Object postid) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByPostid(postid);
	}

	@Override
	public List<PostComment> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByAddtime(addtime);
	}

	@Override
	public List<PostComment> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByUserid(userid);
	}

	@Override
	public List<PostComment> findByText(Object text) {
		// TODO Auto-generated method stub
		return postCommentDAO.findByText(text);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postCommentDAO.findAll();
	}

	@Override
	public PostComment merge(PostComment detachedInstance) {
		// TODO Auto-generated method stub
		return postCommentDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(PostComment instance) {
		// TODO Auto-generated method stub
		postCommentDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(PostComment instance) {
		// TODO Auto-generated method stub
		postCommentDAO.attachClean(instance);
	}

	@Override
	public List commentAndUser(int postid) {
		// TODO Auto-generated method stub
		return postCommentDAO.commentAndUser(postid);
	}

	
}
