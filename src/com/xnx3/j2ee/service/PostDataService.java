package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.PostData;
/**
 * 论坛帖子内容
 * @author 管雷鸣
 *
 */
public interface PostDataService {

	public void save(PostData transientInstance);

	public void delete(PostData persistentInstance);

	public PostData findById(java.lang.Integer id);

	public List<PostData> findByExample(PostData instance);

	public List findByProperty(String propertyName, Object value);

	public List<PostData> findByText(Object text);

	public List findAll();

	public PostData merge(PostData detachedInstance);

	public void attachDirty(PostData instance);

	public void attachClean(PostData instance);

}