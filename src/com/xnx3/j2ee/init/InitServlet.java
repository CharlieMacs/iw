package com.xnx3.j2ee.init;

import java.util.List;
import java.util.Map;

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
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext.getServletContext());
		postService = ctx.getBean("postService", PostService.class);
		sqlService = ctx.getBean("sqlService", SqlService.class);
		
		//判断一下，当 system 表中有数据时，才会加载postClass、role、system等数据库信息。反之，如果system表没有数据，也就是认为开发者刚吧iw框架假设起来，还没有往里填充数据，既然没有数据，便不需要加载这几个数据表的数据了
		List<Map<String,Object>> map = sqlService.findMapBySqlQuery("SHOW TABLES LIKE '%system%'");
		if(map.size() > 0){
			generateCache_postClass();
			new Role().role(sqlService.findAll(com.xnx3.j2ee.entity.Role.class));
			readSystemTable();
		}else{
			Global.databaseCreateFinish = false;
			java.lang.System.out.println("--------------------");
			java.lang.System.out.println("");
			java.lang.System.out.println("");
			java.lang.System.out.println("请访问您当前项目/install/sql.do 进行导入数据");
			java.lang.System.out.println("");
			java.lang.System.out.println("");
			java.lang.System.out.println("--------------------");
		}
		
		new Message();
		new PayLog();
		new SmsLog();
		new User();
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
