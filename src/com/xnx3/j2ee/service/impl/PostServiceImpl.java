package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xnx3.j2ee.dao.PostDAO;
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDAO postDAO;
	
	@Override
	public void save(Post transientInstance) {
		// TODO Auto-generated method stub
		postDAO.save(transientInstance);
	}

	@Override
	public void delete(Post persistentInstance) {
		// TODO Auto-generated method stub
		postDAO.delete(persistentInstance);
	}

	@Override
	public Post findById(Integer id) {
		// TODO Auto-generated method stub
		return postDAO.findById(id);
	}

	@Override
	public List<Post> findByExample(Post instance) {
		// TODO Auto-generated method stub
		return postDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return postDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Post> findByClassid(Object classid) {
		// TODO Auto-generated method stub
		return postDAO.findByClassid(classid);
	}

	@Override
	public List<Post> findByTitle(Object title) {
		// TODO Auto-generated method stub
		return postDAO.findByTitle(title);
	}

	@Override
	public List<Post> findByView(Object view) {
		// TODO Auto-generated method stub
		return postDAO.findByView(view);
	}

	@Override
	public List<Post> findByInfo(Object info) {
		// TODO Auto-generated method stub
		return postDAO.findByInfo(info);
	}

	@Override
	public List<Post> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return postDAO.findByAddtime(addtime);
	}

	public List<Post> findByState(Object state){
		return postDAO.findByState(state);
	}
	
	@Override
	public List<Post> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return postDAO.findByUserid(userid);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postDAO.findAll();
	}

	@Override
	public Post merge(Post detachedInstance) {
		// TODO Auto-generated method stub
		return postDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(Post instance) {
		// TODO Auto-generated method stub
		postDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(Post instance) {
		// TODO Auto-generated method stub
		postDAO.attachClean(instance);
	}

}
