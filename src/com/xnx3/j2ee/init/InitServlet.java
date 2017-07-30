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
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.generateCache.Message;
import com.xnx3.j2ee.generateCache.PayLog;
import com.xnx3.j2ee.generateCache.Role;
import com.xnx3.j2ee.generateCache.SmsLog;
import com.xnx3.j2ee.generateCache.User;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.service.RoleService;
import com.xnx3.j2ee.service.SystemService;

/**
 * 初始化项目，将使用到的一些东东加入Global以方便后续使用
 * @author apple
 */
public class InitServlet extends HttpServlet {
	private PostClassService postClassService;
	private SystemService systemService;
	private RoleService roleService;
	
	@Override
	public void init(ServletConfig servletContext) throws ServletException {
		//获取当前项目的所在路径
		Global.projectPath = servletContext.getServletContext().getRealPath("/");
		
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext.getServletContext());
		postClassService = ctx.getBean("postClassService", PostClassService.class);
		systemService = ctx.getBean("systemService", SystemService.class);
		roleService = ctx.getBean("roleService",RoleService.class);

		generateCache_postClass();
		new Role().role(roleService.findAll());
		loadLogType();
		new Message();
		new PayLog();
		new SmsLog();
		new User();
		
		readSystemTable();
	}
	
	
	/**
	 * 加载Log的type类型，同时将其做js文件缓存
	 */
	public static void loadLogType(){
		Log.typeMap.clear();
		List<String> list = ConfigManagerUtil.getSingleton("systemConfig.xml").getList("logTypeList.type");
		new com.xnx3.j2ee.generateCache.Log().type(list);
    	for (int i = 0; i < list.size(); i++) {
    		String[] array = list.get(i).split("#");
    		String name = array[0];
    		int value = Lang.stringToInt(array[1], 0);
    		String description = array[2];
    		Log.typeMap.put(name, value);
    		Log.typeDescriptionMap.put(value, description);
		}
	}
	
	
	/**
	 * 生成缓存数据
	 */
	public void generateCache_postClass(){
		List<PostClass> list = postClassService.findByIsdelete(BaseEntity.ISDELETE_NORMAL);
		new Bbs().postClass(list);
	}
	
	
	/**
	 * 读system表数据
	 */
	public void readSystemTable(){
		Global.system.clear();
		
		List<com.xnx3.j2ee.entity.System> list = systemService.findAll();
		for (int i = 0; i < list.size(); i++) {
			com.xnx3.j2ee.entity.System s = list.get(i);
			Global.system.put(s.getName(), s.getValue());
		}
	}
	
}
