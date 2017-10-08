package com.xnx3.j2ee.service.impl;

import java.util.List;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.entity.Collect;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.func.Language;
import com.xnx3.j2ee.service.CollectService;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.vo.BaseVO;

public class CollectServiceImpl implements CollectService {
	
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	
	/**
	 * 增加关注
	 * @param userid 要关注的用户的id
	 * @return {@link BaseVO} 若成功，info返回关注成功的 collect.id
	 */
	public BaseVO addCollect(int userid){
		BaseVO baseVO = new BaseVO();
		
		if(ShiroFunc.getUser().getId() == userid){
			baseVO.setBaseVO(BaseVO.FAILURE, Language.show("collect_notCollectOneself"));
			return baseVO;
		}
		
		User user = sqlDAO.findById(User.class, userid);
		if(user == null){
			baseVO.setBaseVO(BaseVO.FAILURE, Language.show("collect_null"));
			return baseVO;
		}
		
		Collect collect = new Collect();
		collect.setOthersid(userid);
		collect.setUserid(ShiroFunc.getUser().getId());
		List<Collect> list = sqlDAO.findByExample(collect);
		if(list.size()>0){
			baseVO.setBaseVO(BaseVO.FAILURE, Language.show("collect_already"));
			return baseVO;
		}
		
		collect.setAddtime(DateUtil.timeForUnix10());
		sqlDAO.save(collect);
		if(collect.getId()>0){
			baseVO.setBaseVO(BaseVO.SUCCESS, collect.getId()+"");
			return baseVO;
		}else{
			baseVO.setBaseVO(BaseVO.FAILURE, Language.show("collect_saveFailure"));
			return baseVO;
		}
	}

	@Override
	public BaseVO cancelCollect(int userid) {
		BaseVO baseVO = new BaseVO();
		
		Collect collect = new Collect();
		collect.setOthersid(userid);
		collect.setUserid(ShiroFunc.getUser().getId());
		List<Collect> list = sqlDAO.findByExample(collect);
		if(list.size()==0){
			baseVO.setBaseVO(BaseVO.FAILURE, Language.show("collect_notCollectSoNotCancel"));
			return baseVO;
		}
		Collect c = list.get(0);
		sqlDAO.delete(c);
		
		return baseVO;
	}
	
	/**
	 * 检索我是否已经关注过此人了。
	 * <br/>只能是登陆状态使用，会自动加入我的userid进行搜索。若未登录，会返回null
	 * @param othersid 检索我是否关注过的人的userid
	 * @return
	 */
	public Collect findMyByOthersid(int othersid){
		User user = ShiroFunc.getUser();
		if(user == null){
			return null;
		}
		Collect c = new Collect();
		c.setUserid(user.getId());
		c.setOthersid(othersid);
		List<Collect> list = sqlDAO.findByExample(c);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
