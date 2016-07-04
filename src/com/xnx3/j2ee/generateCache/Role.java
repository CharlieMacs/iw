package com.xnx3.j2ee.generateCache;
import java.util.List;

/**
 * 权限相关数据缓存生成
 * @author 管雷鸣
 *
 */
public class Role extends BaseGenerate {
	
	/**
	 * {@link com.xnx3.j2ee.entity.Role} id－name 缓存
	 */
	public void role(List<com.xnx3.j2ee.entity.Role> list){
		createCacheObject("role");
		
		for (int i = 0; i < list.size(); i++) {
    		com.xnx3.j2ee.entity.Role role = list.get(i);
    		cacheAdd(role.getId(), role.getName());
		}
		
		appendContent("/* 将 2,3,4 的权限字段转换为会员,超级管理员显示在html */ function writeName(authority){var roleArray=authority.split(',');var s='';for(var i=0;i<roleArray.length;i++){if(s!=''){s=s+','}s=s+role[roleArray[i]]}document.write(s)} ");
		
		generateCacheFile();
	}
	
}
