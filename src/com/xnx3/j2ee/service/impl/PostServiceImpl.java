package com.xnx3.j2ee.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.PostDAO;
import com.xnx3.j2ee.dao.PostDataDAO;
import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostData;
import com.xnx3.j2ee.service.PostService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDAO postDAO;
	@Resource
	private PostDataDAO postDataDAO;
	@Resource
	private LogDAO logDAO;
	
	@Override
	public void save(Post transientInstance) {
		// TODO Auto-generated method stub
		postDAO.save(transientInstance);
	}

	@Override
	public void delete(Post persistentInstance) {
		// TODO Auto-generated method stub
		postDAO.delete(persistentInstance);
	}

	@Override
	public Post findById(Integer id) {
		// TODO Auto-generated method stub
		return postDAO.findById(id);
	}

	@Override
	public List<Post> findByExample(Post instance) {
		// TODO Auto-generated method stub
		return postDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return postDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Post> findByClassid(Object classid) {
		// TODO Auto-generated method stub
		return postDAO.findByClassid(classid);
	}

	@Override
	public List<Post> findByTitle(Object title) {
		// TODO Auto-generated method stub
		return postDAO.findByTitle(title);
	}

	@Override
	public List<Post> findByView(Object view) {
		// TODO Auto-generated method stub
		return postDAO.findByView(view);
	}

	@Override
	public List<Post> findByInfo(Object info) {
		// TODO Auto-generated method stub
		return postDAO.findByInfo(info);
	}

	@Override
	public List<Post> findByAddtime(Object addtime) {
		// TODO Auto-generated method stub
		return postDAO.findByAddtime(addtime);
	}

	public List<Post> findByState(Object state){
		return postDAO.findByState(state);
	}
	
	@Override
	public List<Post> findByUserid(Object userid) {
		// TODO Auto-generated method stub
		return postDAO.findByUserid(userid);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postDAO.findAll();
	}

	@Override
	public Post merge(Post detachedInstance) {
		// TODO Auto-generated method stub
		return postDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(Post instance) {
		// TODO Auto-generated method stub
		postDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(Post instance) {
		// TODO Auto-generated method stub
		postDAO.attachClean(instance);
	}

	@Override
	public BaseVO addPost(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		int id = Lang.stringToInt(request.getParameter("id"), 0);
		int classid = Lang.stringToInt(request.getParameter("classid"), 0);
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		
		if(classid == 0){
			baseVO.setBaseVO(BaseVO.FAILURE, "请选择发布的板块");
			return baseVO;
		}
		if(title==null || title.length()<Global.bbs_titleMinLength || title.length()>Global.bbs_titleMaxLength){
			baseVO.setBaseVO(BaseVO.FAILURE, "标题必须是"+Global.bbs_titleMinLength+"到"+Global.bbs_titleMaxLength+"个字母或汉字");
			return baseVO;
		}
		if(text==null || text.length()<Global.bbs_textMinLength){
			baseVO.setBaseVO(BaseVO.FAILURE, "内容不能少于"+Global.bbs_textMinLength+"个字母或汉字");
			return baseVO;
		}
		
		Post post = new com.xnx3.j2ee.entity.Post();
		PostData postData = new PostData();
		if(id != 0){
			post = findById(id);
			if(post == null){
				baseVO.setBaseVO(BaseVO.FAILURE, "要修改的帖子不存在！");
				return baseVO;
			}else{
				post.setId(id);
				postData = postDataDAO.findById(post.getId());
			}
		}else{
			post.setAddtime(com.xnx3.DateUtil.timeForUnix10());
			post.setState(Post.STATE_NORMAL);
			post.setUserid(ShiroFunc.getUser().getId());
			post.setView(0);
		}
		
		String info="";	//截取简介文字,30字
		if(text.length()<60){
			info=text;
		}else{
			info=text.substring(0,60);
		}
		
		post.setTitle(title);
		post.setClassid(classid);
		post.setInfo(info);
		save(post);
		
		if(postData.getPostid()==null){
			postData.setPostid(post.getId());
		}
		postData.setText(text);
		postDataDAO.save(postData);
		
		baseVO.setBaseVO(BaseVO.SUCCESS, post.getId()+"");
		if(id == 0){
			logDAO.insert(post.getId(), "BBS_POST_ADD", post.getTitle());
		}else{
			logDAO.insert(post.getId(), "BBS_POST_UPDATE", post.getTitle());
		}
		
		return baseVO;
	}

}
