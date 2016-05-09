package com.xnx3.j2ee.generateCache;

/**
 * 信息相关数据缓存生成
 * @author 管雷鸣
 *
 */
public class Message extends BaseGenerate {
	
	/**
	 * message.state 值－描述 缓存
	 * @param list
	 */
	public void state(){
		createCacheObject("state");
		cacheAdd(com.xnx3.j2ee.entity.Message.MESSAGE_STATE_READ, "已读");
		cacheAdd(com.xnx3.j2ee.entity.Message.MESSAGE_STATE_UNREAD, "未读");
		generateCacheFile();
	}

	/**
	 * message.isdelete 值－描述 缓存
	 * @param list
	 */
	public void isdelete(){
		createCacheObject("isdelete");
		cacheAdd(com.xnx3.j2ee.entity.Message.ISDELETE_DELETE, "已删除");
		cacheAdd(com.xnx3.j2ee.entity.Message.ISDELETE_NORMAL, "正常");
		generateCacheFile();
	}
}
