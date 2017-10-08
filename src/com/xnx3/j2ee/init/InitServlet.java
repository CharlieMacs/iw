package com.xnx3.j2ee.init;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.Lang;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.BaseEntity;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.entity.System;
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.generateCache.Message;
import com.xnx3.j2ee.generateCache.PayLog;
import com.xnx3.j2ee.generateCache.Role;
import com.xnx3.j2ee.generateCache.SmsLog;
import com.xnx3.j2ee.generateCache.User;
import com.xnx3.j2ee.service.PostService;
import com.xnx3.j2ee.service.SqlService;

/**
 * 初始化项目，将使用到的一些东东加入Global以方便后续使用
 * @author apple
 */
public class InitServlet extends HttpServlet {
	private PostService postService;
	private SqlService sqlService;
	
	@Override
	public void init(ServletConfig servletContext) throws ServletException {
		//获取当前项目的所在路径
		Global.projectPath = servletContext.getServletContext().getRealPath("/");
		
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext.getServletContext());
		postService = ctx.getBean("postService", PostService.class);
		sqlService = ctx.getBean("sqlService", SqlService.class);
		
		generateCache_postClass();
		new Role().role(sqlService.findAll(com.xnx3.j2ee.entity.Role.class));
		new Message();
		new PayLog();
		new SmsLog();
		new User();
		
		readSystemTable();
	}
	
	/**
	 * 生成缓存数据
	 */
	public void generateCache_postClass(){
		List<PostClass> list = sqlService.findByProperty(PostClass.class, "isdelete", BaseEntity.ISDELETE_NORMAL);
//		List<PostClass> list = postClassService.findByIsdelete(BaseEntity.ISDELETE_NORMAL);
		new Bbs().postClass(list);
	}
	
	/**
	 * 读system表数据
	 */
	public void readSystemTable(){
		Global.system.clear();
		
		List<com.xnx3.j2ee.entity.System> list = sqlService.findAll(com.xnx3.j2ee.entity.System.class);
		for (int i = 0; i < list.size(); i++) {
			com.xnx3.j2ee.entity.System s = list.get(i);
			Global.system.put(s.getName(), s.getValue());
		}
	}
	
}
