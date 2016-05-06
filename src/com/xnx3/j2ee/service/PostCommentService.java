package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostComment;
import com.xnx3.j2ee.vo.BaseVO;
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
	
	/**
	 * 根据帖子id查所有回帖，排序为 id DESC
	 * @param postid 帖子id
	 * @return List<comment.addtime,comment.userid,comment.text,user.head,user.nickname,user.id>
	 */
	public List commentAndUser(int postid);
	
	/**
	 * 根据帖子id查回帖，排序为 id DESC
	 * @param postid 帖子id
	 * @param limit 条数，若为0则显示所有
	 * @return List<comment.addtime,comment.userid,comment.text,user.head,user.nickname,user.id>
	 */
	public List commentAndUser(int postid,int limit);
	
	/**
	 * 根据帖子id查回帖的数量
	 * @param postid 帖子id
	 * @return 回帖数量
	 */
	public int count(int postid);
	
	/**
	 * 删除帖子评论，逻辑删除，改状态isdelete=1
	 * @param id 要删除的评论的id， {@link PostComment}.id
	 * @return {@link BaseVO}
	 */
	public BaseVO deleteComment(int id);
	
	/**
	 * 添加回复，回帖
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单提交项：
	 * 			<ul>
	 * 				<li>postid(帖子的id)  {@link Post}.id)</li>
	 * 				<li>text(回复内容)，发表回帖的内容</li>
	 * 			</ul>
	 * @return {@link BaseVO}
	 */
	public BaseVO addComment(HttpServletRequest request);
}