package com.xnx3.j2ee.generateCache;
import java.util.List;
import com.xnx3.Lang;

/**
 * 信息相关数据缓存生成
 * @author 管雷鸣
 *
 */
public class Log extends BaseGenerate {
	
	/**
	 * log.type 值－描述 缓存
	 */
	public synchronized void type(List<String> list){
		createCacheObject("type");
		
		for (int i = 0; i < list.size(); i++) {
    		String[] array = list.get(i).split("#");
    		String name = array[0];
    		Short value = (short) Lang.stringToInt(array[1], 0);
    		String description = array[2];
    		cacheAdd(value, description);
		}
//		
//		cacheAdd(DBConfigure.LOG_BBS_POST_ADD, "发表帖子");
//		cacheAdd(DBConfigure.LOG_BBS_POST_COMMENT_ADD, "发表帖子评论");
//		cacheAdd(DBConfigure.LOG_BBS_POST_VIEW, "查看帖子");
//		
//		cacheAdd(DBConfigure.LOG_USER_LOGOUT, "注销登录");
//		cacheAdd(DBConfigure.LOG_USER_LOGIN_SUCCESS, "登录系统");
//		cacheAdd(DBConfigure.LOG_USER_UPDATEHEAD,"更改头像");
//		cacheAdd(DBConfigure.LOG_USER_UPDATEPASSWORD,"更改密码");
//		cacheAdd(DBConfigure.LOG_USER_UPDATE_NICKNAME,"更改昵称");
//		cacheAdd(DBConfigure.LOG_USER_EMAIL_INVITE,"邮件邀请注册");
//		cacheAdd(DBConfigure.LOG_USER_INVITEREG_AWARD,"邀请注册后奖励");
//		
//		cacheAdd(DBConfigure.LOG_FRIEND_ADD,"添加好友");
//		cacheAdd(DBConfigure.LOG_FRIEND_DELETE,"删除好友");
//		
//		cacheAdd(DBConfigure.LOG_MESSAGE_READ,"查看未读信息");
//		
//		cacheAdd(DBConfigure.LOG_PAINT_ADD,"创建图表");
//		cacheAdd(DBConfigure.LOG_PAINT_COMMENT_ADD,"添加图表评论");
//		
//		cacheAdd(DBConfigure.LOG_PROJECT_ADD,"添加项目组");
//		cacheAdd(DBConfigure.LOG_PROJECT_DELETE,"删除项目组");
//		cacheAdd(DBConfigure.LOG_PROJECT_ADD_MEMBER,"项目组添加成员");
//		
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_GENERATEALLCACHE,"刷新所有缓存数据");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_REG_ROLE,"修改用户注册后的权限");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_EDITSYSTEM,"修改系统参数设置");
//		
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_ROLE_SAVE,"添加/修改角色");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_ROLE_DELETE,"删除角色");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_PERMISSION_SAVE,"添加/修改资源permission");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_PERMISSION_DELETE,"删除资源permission");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_ROLE_PERMISSION_SAVE,"编辑角色所属的资源permission");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_USER_ROLE_SAVE,"编辑角色所属的资源permission");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_MESSAGE_DELETE,"后台删除站内信息");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_BBS_POST_DELETE,"后台删除帖子");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_BBS_CLASS_SAVE,"添加/修改板块");
//		cacheAdd(DBConfigure.LOG_ADMIN_SYSTEM_BBS_CLASS_DELETE,"删除板块");
		
		generateCacheFile();
	}
	
}
