package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.entity.Message;
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostData;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.j2ee.vo.PostVO;
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
	public BaseVO savePost(HttpServletRequest request);
	
	/**
	 * 删除帖子，逻辑删除，改状态isdelete=1
	 * @param id 要删除的帖子的id，post.id
	 * @return {@link BaseVO}
	 */
	public BaseVO deletePost(int id);
	
	/**
	 * 查看，阅读帖子，通过 {@link Post}.id获取此条帖子的具体信息，包含 {@link Post}、 {@link PostData}
	 * @param id {@link Post}.id
	 * @return {@link PostVO}
	 * 			<br/>首先判断getResult()是否是 {@link BaseVO#SUCCESS}，若是，才可以调取其他的值。若不是，可通过getInfo()获取错误信息
	 */
	public PostVO read(int id);
}