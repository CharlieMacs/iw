package com.xnx3.j2ee.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.dao.GlobalDAO;
import com.xnx3.j2ee.service.GlobalService;
import com.xnx3.j2ee.util.Page;

@Service("globalService")
public class GlobalServiceImpl implements GlobalService {

	@Resource
	private GlobalDAO globalDAO;
	
	@Override
	public int count(String tableName, String where) {
		// TODO Auto-generated method stub
		return globalDAO.count(tableName, where);
	}

	@Override
	public List findBySqlQuery(String selectFrom, String where, Page page,Class entityClass) {
		return globalDAO.findBySqlQuery(selectFrom, where, page,entityClass);
	}

	@Override
	public List<Map<String, String>> findBySqlQuery(String sql, Page page) {
		return globalDAO.findBySqlQuery(sql,page);
	}

}
