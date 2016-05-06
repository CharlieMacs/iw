package com.xnx3.j2ee.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.vo.BaseVO;
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
	
	public List<PostClass> findByIsdelete(Object isdelete);

	public List findAll();

	public PostClass merge(PostClass detachedInstance);

	public void attachDirty(PostClass instance);

	public void attachClean(PostClass instance);
	
	/**
	 * 添加、修改板块
	 * @param request {@link HttpServletRequest}
	 * 			<br/>form表单提交项：
	 * 			<ul>
	 * 				<li>id(板块id， {@link PostClass}.id)，若有id，则是修改，若无id，则是新增板块</li>
	 * 				<li>name(板块名)，新增的板块的名字</li>
	 * 			</ul>
	 * @return {@link BaseVO} 若成功，info传回板块的id
	 */
	public BaseVO savePostClass(HttpServletRequest request);
	
	/**
	 * 删除板块，逻辑删除，改状态isdelete=1
	 * @param id 要删除的板块的id，PostClass.id
	 * @return {@link BaseVO}
	 */
	public BaseVO deletePostClass(int id);
}