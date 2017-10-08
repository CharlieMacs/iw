package com.xnx3.j2ee.generateCache;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xnx3.j2ee.entity.Post;
import com.xnx3.j2ee.entity.PostClass;

/**
 *  论坛相关缓存
 * @author 管雷鸣
 *
 */
@Component
public class Bbs extends BaseGenerate {
	public Bbs() {
		state();
	}
	
	/**
	 * 生成论坛板块数据缓存，数组存在
	 * @param list 传入如：postClassService.findAll()
	 */
	public void postClass(List<PostClass> list){
		createCacheObject("classid");
		for (int i = 0; i < list.size(); i++) {
			PostClass pc = list.get(i);
			cacheAdd(pc.getId(), pc.getName());
		}
		generateCacheFile();
	}
	
	public void state(){
		createCacheObject("state");
		cacheAdd(Post.STATE_NORMAL, "正常");
		cacheAdd(Post.STATE_AUDITING, "审核中");
		cacheAdd(Post.STATE_INCONGRUENT, "不符合要求");
		cacheAdd(Post.STATE_LOCK, "锁定冻结");
		generateCacheFile();
	}
}
