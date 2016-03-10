package com.xnx3.j2ee.generateCache;

import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.Global;

public class BaseGenerate {
	/**
	 * 生成js缓存文件的内容
	 */
	private String content;
	
	/**
	 * 生成的js存储数据的对象名，保存的文件名也是使用此有关联
	 */
	private String objName;	
	
	/**
	 * 创建js对象
	 * @param objName js对象名（保存的js文件名、使用时引用的js对象名）
	 */
	void createCacheObject(String objName){
		this.objName=objName;
		content="var "+objName+" = new Array(); ";
	}
	
	/**
	 * 往缓存js对象中增加键值对
	 * @param key 键
	 * @param value 值
	 */
	void cacheAdd(Object key,Object value){
		content += objName+"['"+key+"']='"+value+"'; ";
	}
	
	/**
	 * 生成js缓存文件保存
	 */
	void generateCacheFile(){
		FileUtil.write(Global.projectPath+Global.CACHE_FILE+getClass().getSimpleName()+"_"+objName+".js", content);
		this.content=null;
	}
	

}

