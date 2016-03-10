package com.xnx3.j2ee.generateCache;

import java.util.List;

import com.xnx3.j2ee.entity.PostClass;

/**
 *  论坛相关缓存
 * @author 管雷鸣
 *
 */
public class Bbs extends BaseGenerate {
	
	/**
	 * 生成论坛板块数据缓存，数组存在
	 * @param list 传入如：postClassService.findAll()
	 */
	public synchronized void postClass(List<PostClass> list){
		createCacheObject("postClass");
		for (int i = 0; i < list.size(); i++) {
			PostClass pc = list.get(i);
			cacheAdd(pc.getId(), pc.getName());
		}
		generateCacheFile();
	}
	
	
}
