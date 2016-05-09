package com.xnx3.j2ee.generateCache;
import java.util.List;
import com.xnx3.Lang;

/**
 * 信息相关数据缓存生成
 * @author 管雷鸣
 *
 */
public class User extends BaseGenerate {
	
	/**
	 * log.type 值－描述 缓存
	 */
	public void isfreeze(){
		createCacheObject("isfreeze");
    	cacheAdd(com.xnx3.j2ee.entity.User.ISFREEZE_NORMAL, "正常");
    	cacheAdd(com.xnx3.j2ee.entity.User.ISFREEZE_FREEZE, "冻结");
		generateCacheFile();
	}
	
}
