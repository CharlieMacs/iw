package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.PostClass;
/**
 * 论坛板块
 * @author 管雷鸣
 *
 */
public interface PostClassService {

	public void save(PostClass transientInstance);

	public void delete(PostClass persistentInstance);

	public PostClass findById(java.lang.Integer id);

	public List<PostClass> findByExample(PostClass instance);

	public List findByProperty(String propertyName, Object value);

	public List<PostClass> findByName(Object name);

	public List findAll();

	public PostClass merge(PostClass detachedInstance);

	public void attachDirty(PostClass instance);

	public void attachClean(PostClass instance);

}