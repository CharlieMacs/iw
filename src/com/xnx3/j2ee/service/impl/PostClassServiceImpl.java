package com.xnx3.j2ee.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.xnx3.Lang;
import com.xnx3.j2ee.dao.LogDAO;
import com.xnx3.j2ee.dao.PostClassDAO;
import com.xnx3.j2ee.entity.BaseEntity;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.vo.BaseVO;

public class PostClassServiceImpl implements PostClassService {

	private PostClassDAO postClassDAO;
	private LogDAO logDAO;
	
	public PostClassDAO getPostClassDAO() {
		return postClassDAO;
	}

	public void setPostClassDAO(PostClassDAO postClassDAO) {
		this.postClassDAO = postClassDAO;
	}

	public LogDAO getLogDAO() {
		return logDAO;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	@Override
	public void save(PostClass transientInstance) {
		// TODO Auto-generated method stub
		postClassDAO.save(transientInstance);
	}

	@Override
	public void delete(PostClass persistentInstance) {
		// TODO Auto-generated method stub
		postClassDAO.delete(persistentInstance);
	}

	@Override
	public PostClass findById(Integer id) {
		// TODO Auto-generated method stub
		return postClassDAO.findById(id);
	}

	@Override
	public List<PostClass> findByExample(PostClass instance) {
		// TODO Auto-generated method stub
		return postClassDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return postClassDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<PostClass> findByName(Object name) {
		// TODO Auto-generated method stub
		return postClassDAO.findByName(name);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return postClassDAO.findAll();
	}

	@Override
	public PostClass merge(PostClass detachedInstance) {
		// TODO Auto-generated method stub
		return postClassDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(PostClass instance) {
		// TODO Auto-generated method stub
		postClassDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(PostClass instance) {
		// TODO Auto-generated method stub
		postClassDAO.attachClean(instance);
	}

	@Override
	public BaseVO savePostClass(HttpServletRequest request) {
		BaseVO baseVO = new BaseVO();
		int id = Lang.stringToInt(request.getParameter("id"), 0);
		String name = request.getParameter("name");
		if(name == null || name.length()==0){
			baseVO.setBaseVO(BaseVO.FAILURE, "板块的名字不能为空");
			return baseVO;
		}
		
		PostClass postClass = null;
		if(id==0){	//新增
			postClass = new PostClass();
		}else{
			//修改
			postClass = findById(id);
			if(postClass == null){
				baseVO.setBaseVO(BaseVO.FAILURE, "要修改的板块不存在");
				return baseVO;
			}
		}
		
		postClass.setName(name);
		postClass.setIsdelete(BaseEntity.ISDELETE_NORMAL);
		
		save(postClass);
		if((id==0 && postClass.getId()>0) || id > 0){
			if(id>0){
				logDAO.insert(postClass.getId(), "ADMIN_SYSTEM_BBS_CLASS_SAVE", postClass.getName());
			}else{
				logDAO.insert(postClass.getId(), "ADMIN_SYSTEM_BBS_CLASS_ADD", postClass.getName());
			}
			new Bbs().postClass(findByIsdelete(BaseEntity.ISDELETE_NORMAL));
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "操作失败");
		}
		return baseVO;
	}

	@Override
	public BaseVO deletePostClass(int id) {
		BaseVO baseVO = new BaseVO();
		if(id>0){
			PostClass pc = findById(id);
			if(pc!=null){
				pc.setIsdelete(BaseEntity.ISDELETE_DELETE);
				save(pc);
				logDAO.insert(pc.getId(), "ADMIN_SYSTEM_BBS_POST_DELETE", pc.getName());
				new Bbs().postClass(findByIsdelete(BaseEntity.ISDELETE_NORMAL));
			}else{
				baseVO.setBaseVO(BaseVO.FAILURE, "要删除的板块不存在！");
			}
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, "请传入要删除的板块编号");
		}
		return baseVO;
	}

	@Override
	public List<PostClass> findByIsdelete(Object isdelete) {
		return postClassDAO.findByIsdelete((Short)isdelete);
	}

}
