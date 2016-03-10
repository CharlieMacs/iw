package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.PostComment;
/**
 * 论坛帖子评论
 * @author 管雷鸣
 *
 */
public interface PostCommentService {

	public void save(PostComment transientInstance);

	public void delete(PostComment persistentInstance);

	public PostComment findById(java.lang.Integer id);

	public List<PostComment> findByExample(PostComment instance);

	public List findByProperty(String propertyName, Object value);

	public List<PostComment> findByPostid(Object postid);

	public List<PostComment> findByAddtime(Object addtime);

	public List<PostComment> findByUserid(Object userid);

	public List<PostComment> findByText(Object text);

	public List findAll();

	public PostComment merge(PostComment detachedInstance);

	public void attachDirty(PostComment instance);

	public void attachClean(PostComment instance);
	
	public List commentAndUser(int postid);
}