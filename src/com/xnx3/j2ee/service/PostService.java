package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.vo.BaseVO;
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
	
	/**
	 * 发表帖子
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单提交项：
	 * 			<ul>
	 * 				<li>id(帖子id)，若有id，则是修改，若无id，则是新增发表帖子</li>
	 * 				<li>title(标题)，发表帖子的标题</li>
	 * 				<li>text(帖子内容)，发表帖子的内容</li>
	 * 				<li>classid(postClass.id)，发表的帖子属于哪个分类</li>
	 * 			</ul>
	 * @return {@link BaseVO} 若成功，info传回帖子的id
	 */
	public BaseVO addPost(HttpServletRequest request);
}