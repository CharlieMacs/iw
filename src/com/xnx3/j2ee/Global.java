package com.xnx3.j2ee;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础配置、集中管理
 * @author 管雷鸣
 *
 */
public class Global {
	
	/**
	 * 用户可自由切换当前语言，此处为语言库配置，使用哪种。默认根据IP智能判断，若是在中国则使用简体中文，不在中国一律使用英语
	 * chineseSimple：简体中文
	 * english:英语
	 */
	public final static String language="chineseSimple";
	
	/***邮件发送系统***/
	public final static String MAIL_LOGIN_USERNAME="xnx3_cs@163.com";	//邮箱登陆账号
	public final static String MAIL_LOGIN_PASSWORD="cccccc";				//邮箱登陆密码
	
	public final static int MESSAGE_CONTENT_MINLENGTH = 2;		//发送站内信时短信内容允许的最小字符	
	public final static int MESSAGE_CONTENT_MAXLENGTH = 100;	//发送站内信时短信内容允许的最大字符
	
	public final static int DEFAULT_BBS_CREATEPOST_CLASSID=1;	//发帖时，如果没有选择发帖到哪，这里默认选中的那个分类id
	public final static int POST_INFO_AUTOCAT_MAX=60;		//发帖时，自动截取内容前多少个字节作为简介。这里是截取的开头的最大字符
	
	/********文件目录相关，会在当前项目的根目录下的文件夹*********/
	public final static String CACHE_FILE="cache/js/";
	
	/********文件目录相关，用户头像存在于当前项目的根目录下的文件夹*********/
	public final static String USER_HEAD_FILE="upload/userHead/";
	
	/********** 动态参数，会在项目启动时加载 ***********/
	public static String projectPath=null;	//当前项目再硬盘的路径，绝对路径
	
	/***** system表的参数,name-value ******/
	public static Map<String, String> system = new HashMap<String, String>();

	/**********固定参数**********/
	public final static int USER_PASSWORD_SALT_NUMBER=2;	//密码散列几次，2即可,需要跟配置文件的散列次数一致
	public final static int PROMPT_STATE_SUCCESS=1;			//中专提示页面prompt.jsp的成功提示状态
	public final static int PROMPT_STATE_ERROR=0;			//中专提示页面prompt.jsp的失败（错误）提示状态
	public final static int PAGE_DEFAULT_EVERYNUMBER=20;	//用户前台分页，默认每页20行记录
	public final static int PAGE_ADMIN_DEFAULT_EVERYNUMBER=10;	//后台分业，每页10条纪录
	
	
	/**
	 * 返回 system 表的值
	 * @param systemName
	 * @return
	 */
	public static String get(String systemName){
		return system.get(systemName);
	}
}
