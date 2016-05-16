package com.xnx3.j2ee.generateCache;

import java.io.IOException;

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
	public void createCacheObject(String objName){
		this.objName=objName;
		content="var "+objName+" = new Array(); ";
	}
	
	/**
	 * 往缓存js对象中增加键值对
	 * @param key 键
	 * @param value 值
	 */
	public void cacheAdd(Object key,Object value){
		content += objName+"['"+key+"']='"+value+"'; ";
	}
	
	/**
	 * 生成js缓存文件保存
	 */
	public void generateCacheFile(){
		addCommonJsFunction();
		try {
			FileUtil.write(Global.projectPath+Global.CACHE_FILE+getClass().getSimpleName()+"_"+objName+".js", content,FileUtil.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.content=null;
	}
	
	/**
	 * 增加一些常用的js函数
	 */
	public void addCommonJsFunction(){
		this.content+= "/*页面上输出选择框的所有option，显示到页面上*/ function writeSelectAllOptionFor"+this.objName+"(selectId){ var content = \"\"; if(selectId==''){ content = content + '<option value=\"\" selected=\"selected\">所有</option>'; }else{ content = content + '<option value=\"\">所有</option>'; } for(var p in "+this.objName+"){ if(p == selectId){ content = content+'<option value=\"'+p+'\" selected=\"selected\">'+"+this.objName+"[p]+'</option>'; }else{ content = content+'<option value=\"'+p+'\">'+"+this.objName+"[p]+'</option>'; } } document.write(content); }";
	}
	
	/**
	 * 向写出的js文件里增加内容
	 */
	public void appendContent(String content){
		this.content = this.content+" "+content;
	}
}

