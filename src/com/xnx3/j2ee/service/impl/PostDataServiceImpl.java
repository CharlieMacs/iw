package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.PostDataDAO;
import com.xnx3.j2ee.entity.PostData;
import com.xnx3.j2ee.service.PostDataService;

public class PostDataServiceImpl implements PostDataService {
	
	private PostDataDAO postDataDAO;

	public PostDataDAO getPostDataDAO() {
		return postDataDAO;
	}

	public void setPostDataDAO(PostDataDAO postDataDAO) {
		this.postDataDAO = postDataDAO;
	}

	@Override
	public void save(PostData transientInstance) {
		// TODO Auto-generated method stub
		postDataDAO.save(transientInstance);
	}

	@Override
	public void delete(PostData persistentInstance) {
		// TODO Auto-generated method stub
		postDataDAO.delete(persistentInstance);
	}

	@Override
	public List<PostData> findByExample(PostData instance) {
		// TODO Auto-generated method stub
		return postDataDAO.findByExample(instance);
	}


	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postDataDAO.findAll();
	}

	@Override
	public PostData merge(PostData detachedInstance) {
		// TODO Auto-generated method stub
		return postDataDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(PostData instance) {
		// TODO Auto-generated method stub
		postDataDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(PostData instance) {
		// TODO Auto-generated method stub
		postDataDAO.attachClean(instance);
	}

	@Override
	public PostData findById(Integer id) {
		// TODO Auto-generated method stub
		return postDataDAO.findById(id);
	}

	@Override
	public List<PostData> findByText(Object text) {
		// TODO Auto-generated method stub
		return postDataDAO.findByText(text);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return postDataDAO.findByProperty(propertyName, value);
	}

}
