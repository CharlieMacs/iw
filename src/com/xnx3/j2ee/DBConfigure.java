package com.xnx3.j2ee;
/**
 * 数据库常亮关联参数
 * @author 管雷鸣
 *
 */
public class DBConfigure {
	
	/**** message站内信表 ****/
//	public final static Short MESSAGE_STATE_READ=1;	//已读
//	public final static Short MESSAGE_STATE_UNREAD=0;	//未读
	
	/**** system 系统参数的设置 ****/
	public final static Short SYSTEM_LISTSHOW_SHOW=1;		//此项会再后台系统管理列表显示
	public final static Short SYSTEM_LISTSHOW_UNSHOW=0;	//此项不会再后台系统管理列表显示
	
	/****** log ******/
	public final static Short LOG_USER_LOGOUT=10;			//用户注销登录
	public final static Short LOG_USER_LOGIN_SUCCESS=11;		//用户登录成功
	public final static Short LOG_USER_UPDATEHEAD=12;		//修改头像
	public final static Short LOG_USER_UPDATEPASSWORD=13;	//修改密码
	public final static Short LOG_USER_EMAIL_INVITE=14;		//邮件邀请注册
	public final static Short LOG_USER_UPDATE_NICKNAME=15;	//修改昵称
	public final static Short LOG_USER_INVITEREG_AWARD=16;	//邀请注册增加站内货币奖励
	public final static Short LOG_BBS_POST_ADD=21;			//发表帖子
	public final static Short LOG_BBS_POST_COMMENT_ADD=22;//发表帖子评论
	public final static Short LOG_BBS_POST_VIEW=23;			//查看帖子
	public final static Short LOG_FRIEND_ADD=31;				//添加好友
	public final static Short LOG_FRIEND_DELETE=32;			//删除好友
	public final static Short LOG_MESSAGE_READ=41;			//查看未读信息
	public final static Short LOG_PAINT_ADD=51;				//创建图表
	public final static Short LOG_PAINT_COMMENT_ADD=52;	//添加图表评论
	public final static Short LOG_PROJECT_ADD=61;			//添加项目组
	public final static Short LOG_PROJECT_DELETE=62;			//删除项目组
	public final static Short LOG_PROJECT_ADD_MEMBER=63;	//项目组添加成员
	public final static Short LOG_ADMIN_SYSTEM_GENERATEALLCACHE=71;	//刷新所有缓存数据（系统设置）
	public final static Short LOG_ADMIN_SYSTEM_REG_ROLE=72;			//修改用户注册后的权限
	public final static Short LOG_ADMIN_SYSTEM_EDITSYSTEM=73;			//修改系统参数设置
	public final static Short LOG_ADMIN_SYSTEM_ROLE_SAVE=81;			//添加/修改角色
	public final static Short LOG_ADMIN_SYSTEM_ROLE_DELETE=82;			//删除角色
	public final static Short LOG_ADMIN_SYSTEM_PERMISSION_SAVE=91;			//添加/修改资源permission
	public final static Short LOG_ADMIN_SYSTEM_PERMISSION_DELETE=92;		//删除资源permission
	public final static Short LOG_ADMIN_SYSTEM_ROLE_PERMISSION_SAVE=101;	//编辑角色所属的资源permission
	public final static Short LOG_ADMIN_SYSTEM_USER_ROLE_SAVE=111;			//编辑角色所属的资源permission
	public final static Short LOG_ADMIN_SYSTEM_MESSAGE_DELETE=121;		//后台删除站内信息
	public final static Short LOG_ADMIN_SYSTEM_BBS_POST_DELETE=131;		//后台删除帖子
	public final static Short LOG_ADMIN_SYSTEM_BBS_CLASS_SAVE=141;			//添加/修改板块
	public final static Short LOG_ADMIN_SYSTEM_BBS_CLASS_DELETE=142;		//删除板块
	
}
