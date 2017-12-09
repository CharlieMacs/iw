package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.SqlDAO;
import com.xnx3.j2ee.entity.System;
import com.xnx3.j2ee.service.SystemService;

public class SystemServiceImpl implements SystemService {
	
	private SqlDAO sqlDAO;

	public SqlDAO getSqlDAO() {
		return sqlDAO;
	}

	public void setSqlDAO(SqlDAO sqlDAO) {
		this.sqlDAO = sqlDAO;
	}

	@Override
	public void refreshSystemCache() {
		Global.system.clear();
		Global.systemForInteger.clear();
		List<com.xnx3.j2ee.entity.System> list = sqlDAO.findAll(System.class);
		for (int i = 0; i < list.size(); i++) {
			com.xnx3.j2ee.entity.System s = list.get(i);
			Global.system.put(s.getName(), s.getValue());
		}
	}
	
}
