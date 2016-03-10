package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Post;
/**
 * 论坛帖子
 * @author 管雷鸣
 *
 */
public interface PostService {

	public void save(Post transientInstance);

	public void delete(Post persistentInstance);

	public Post findById(java.lang.Integer id);

	public List<Post> findByExample(Post instance);

	public List findByProperty(String propertyName, Object value);

	public List<Post> findByClassid(Object classid);

	public List<Post> findByTitle(Object title);

	public List<Post> findByView(Object view);

	public List<Post> findByInfo(Object info);

	public List<Post> findByAddtime(Object addtime);

	public List<Post> findByUserid(Object userid);

	public List<Post> findByState(Object state);
	
	public List findAll();

	public Post merge(Post detachedInstance);

	public void attachDirty(Post instance);

	public void attachClean(Post instance);

}