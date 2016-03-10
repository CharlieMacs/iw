package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.PostClassDAO;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.service.PostClassService;

@Service("postClassService")
public class PostClassServiceImpl implements PostClassService {

	@Resource
	private PostClassDAO postClassDAO;
	
	@Override
	public void save(PostClass transientInstance) {
		// TODO Auto-generated method stub
		postClassDAO.save(transientInstance);
	}

	@Override
	public void delete(PostClass persistentInstance) {
		// TODO Auto-generated method stub
		postClassDAO.delete(persistentInstance);
	}

	@Override
	public PostClass findById(Integer id) {
		// TODO Auto-generated method stub
		return postClassDAO.findById(id);
	}

	@Override
	public List<PostClass> findByExample(PostClass instance) {
		// TODO Auto-generated method stub
		return postClassDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostClass> findByName(Object name) {
		// TODO Auto-generated method stub
		return postClassDAO.findByName(name);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postClassDAO.findAll();
	}

	@Override
	public PostClass merge(PostClass detachedInstance) {
		// TODO Auto-generated method stub
		return postClassDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(PostClass instance) {
		// TODO Auto-generated method stub
		postClassDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(PostClass instance) {
		// TODO Auto-generated method stub
		postClassDAO.attachClean(instance);
	}

}
