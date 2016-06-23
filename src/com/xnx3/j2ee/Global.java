package com.xnx3.j2ee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.Lang;

/**
 * 基础配置、集中管理
 * @author 管雷鸣
 *
 */
public class Global {
	
	/**
	 * 用户可自由切换当前语言，此处为语言库配置，使用哪种。默认根据IP智能判断，若是在中国则使用简体中文，不在中国一律使用英语
	 * chinese:简体中文
	 * english:英语
	 */
	public static String language_default="chinese";
	/**
	 * language.get("chinese").get("collect_notCollectOneself")
	 */
	public static Map<String, Map<String, String>> language; 
	
	/****站内信****/
	public static boolean MESSAGE_USED = true;			//是否使用站内信息功能，若开启，则访问任何页面都会提前读数据库判断是否有新的未读信息 	systemConfig.xml
	public static int MESSAGE_CONTENT_MINLENGTH = 2;		//发送站内信时短信内容允许的最小字符	systemConfig.xml
	public static int MESSAGE_CONTENT_MAXLENGTH = 100;		//发送站内信时短信内容允许的最大字符 systemConfig.xml
	
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
	public final static int PAGE_ADMIN_DEFAULT_EVERYNUMBER=10;	//后台分页，每页10条纪录
	
	/*****论坛相关******/
	public static int bbs_titleMinLength;	//发帖标题允许的最小长度（英文长度）
	public static int bbs_titleMaxLength;	//发帖标题允许的最大长度（英文长度），最大值同时取决于数据库字段的最大值限制
	public static int bbs_textMinLength;	//内容所允许的最小长度（英文长度)
	public static boolean bbs_readPost_addLog;	//是否将阅读帖子写日志进行记录
	public static int bbs_commentTextMinLength;	//回帖，内容所允许的最小长度（英文长度)
	public static int bbs_commentTextMaxLength;	//回帖，内容所允许的最大长度（英文长度)
	public static int DEFAULT_BBS_CREATEPOST_CLASSID=1;	//发帖时，如果没有选择发帖到哪，这里默认选中的那个分类id systemConfig.xml
	public static int POST_INFO_AUTOCAT_MAX=60;		//发帖时，自动截取内容前多少个字节作为简介。这里是截取的开头的最大字符 systemConfig.xml
	
	/**** 文件上传 *****/
	public static String ossFileUploadImageSuffixList = ""; //图片文件，允许上传的图片的后缀名，多个以|分割，如 "png|jpg|gif"
	
	static{
		/****站内信****/
		MESSAGE_USED = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("message.used").equals("true");
		MESSAGE_CONTENT_MINLENGTH = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("message.content_minlength"), 0);
		MESSAGE_CONTENT_MAXLENGTH = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("message.content_maxlength"), 0);
		
		/*****论坛相关******/
		bbs_titleMinLength = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.titleMinLength"), 0);
		bbs_titleMaxLength = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.titleMaxLength"), 0);
		bbs_textMinLength = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.textMinLength"), 0);
		bbs_readPost_addLog = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.readPost_addLog").equals("true");
		bbs_commentTextMinLength = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.commentTextMinLength"), 0);
		bbs_commentTextMaxLength = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.commentTextMaxLength"), 0);
		DEFAULT_BBS_CREATEPOST_CLASSID = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.default_createPost_classId"), 0);
		POST_INFO_AUTOCAT_MAX = Lang.stringToInt(ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("bbs.post_info_autoCat_max"), 0);
		
		
		/****图片文件上传 ****/
		ossFileUploadImageSuffixList = ConfigManagerUtil.getSingleton("systemConfig.xml").getValue("OSS.imageSuffixList");
		
		/*****语言包*****/
		language_default = ConfigManagerUtil.getSingleton("language.xml").getValue("defaultLanguage");
		loadLanguagePackage();
	}
	
	/**
	 * 从language.xml中获取语言包的名字
	 * 
	 */
	private static void loadLanguagePackage(){
		language = new HashMap<String, Map<String,String>>();
		
		ConfigManagerUtil config = ConfigManagerUtil.getSingleton("language.xml");
		Iterator englishIt = config.getFileConfiguration().getKeys();
		Map<String, String> languageMap = new HashMap<String, String>();
		while(englishIt.hasNext()){
			String key = (String) englishIt.next();
			if(key.indexOf(".")>-1){
				String[] l = key.split("\\.");
				languageMap.put(l[0], l[0]);
			}
		}
		
		Iterator<Map.Entry<String, String>> it = languageMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			loadLanguagePackage(entry.getKey());
		}
	}
	
	/**
	 * 从language.xml加载语言包
	 * @param languageName 语言名，如chinese、english等
	 */
	private static void loadLanguagePackage(String languageName){
		ConfigManagerUtil config = ConfigManagerUtil.getSingleton("language.xml");
		Iterator englishIt = config.getFileConfiguration().getKeys(languageName);
		Map<String, String> englishMap = new HashMap<String, String>();
		while(englishIt.hasNext()){
			String key = (String) englishIt.next();
			String value = config.getValue(key);
			englishMap.put(key.replace(languageName+".", ""), value);
		}
		language.put(languageName, englishMap);
	}
	
	/**
	 * 返回 system 表的值
	 * @param systemName
	 * @return
	 */
	public static String get(String systemName){
		return system.get(systemName);
	}
	
	/**
	 * 获取语言包language.xml中的描述文字。如果没有找到返回空字符""
	 * @param key
	 * @return
	 */
	public static String getLanguage(String key){
		String value = language.get(language_default).get(key);
		if(value == null){
			value = "";
		}
		return value;
	}
	
	public static void main(String[] args) {
	}
}
