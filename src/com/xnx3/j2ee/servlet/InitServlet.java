package com.xnx3.j2ee.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import org.hibernate.Query;
import org.hibernate.Session;
import com.xnx3.Lang;
import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.dao.HibernateSessionFactory;
import com.xnx3.j2ee.entity.PostClass;
import com.xnx3.j2ee.generateCache.Bbs;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.generateCache.Message;
import com.xnx3.j2ee.util.ConfigManagerUtil;

/**
 * 初始化项目，将使用到的一些东东加入Global以方便后续使用
 * @author apple
 */
public class InitServlet extends HttpServlet {
	private Session session = null;
	
	public InitServlet() {
		session=HibernateSessionFactory.getSession();
		
		String path = getClass().getResource("/").getPath();
		Global.projectPath = path.replace("WEB-INF/classes/", "");
		
		initCacheFolder();
		generateCache_postClass();
		new Message().state();
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
		Query query = session.createSQLQuery("SELECT id,name FROM post_class");
		List<PostClass> list = new ArrayList<PostClass>();
		List l=query.list();
		Iterator it=l.iterator();
		while(it.hasNext()){
			Object obj[] = (Object[]) it.next();
			PostClass postClass = new PostClass();
			postClass.setId(Lang.stringToInt(obj[0].toString(), 0));
			postClass.setName(obj[1].toString());
			list.add(postClass);
		}
		new Bbs().postClass(list);
	}
	
	/**
	 * 读system表数据
	 */
	public void readSystemTable(){
		Global.system.clear();
	
		Query q=session.createSQLQuery("select trim(name),trim(value) from System");
		List list=q.list();
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object obj[] = (Object[]) it.next();
			Global.system.put(obj[0].toString(), obj[1].toString());
		}
//		HibernateSessionFactory.closeSession();
	}
	
}
