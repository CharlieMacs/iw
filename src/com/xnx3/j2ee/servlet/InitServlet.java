package com.xnx3.j2ee.servlet;

import java.io.File;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.Lang;
import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.BaseEntity;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.generateCache.Message;
import com.xnx3.j2ee.service.PostClassService;
import com.xnx3.j2ee.service.SystemService;

/**
 * 初始化项目，将使用到的一些东东加入Global以方便后续使用
 * @author apple
 */
public class InitServlet extends HttpServlet {
	private PostClassService postClassService;
	private SystemService systemService;
	
	@Override
	public void init(ServletConfig servletContext) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext.getServletContext());
		postClassService = ctx.getBean("postClassService", PostClassService.class);
		systemService = ctx.getBean("systemService", SystemService.class);

		String path = getClass().getResource("/").getPath();
		Global.projectPath = path.replace("WEB-INF/classes/", "");
		
		initCacheFolder();
		new Bbs().state();
		generateCache_postClass();
		new Message().state();
		new Message().isdelete();
		loadLogType();
		
		readSystemTable();
	}
	
	/**
	 * 初始化缓存文件夹，若根目录下没有缓存文件夹，自动创建
	 */
	public void initCacheFolder(){
		String[] folders = Global.CACHE_FILE.split("/");
		String path = Global.projectPath;
		for (int i = 0; i < folders.length; i++) {
			if(folders[i].length()>0&&!FileUtil.exists(path+folders[i])){
				File file = new File(path+folders[i]);
				file.mkdir();
			}
			path = path+folders[i]+"/";
		}
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
    		Short value = (short) Lang.stringToInt(array[1], 0);
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
//		List<PostClass> list = postClassService.findAll();
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
