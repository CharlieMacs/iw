package com.xnx3.j2ee.func;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.j2ee.Global;
import com.xnx3.j2ee.entity.User;
import com.xnx3.j2ee.shiro.ShiroFunc;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.net.AliyunLogUtil;

/**
 * 会员动作日志的缓存及使用。
 * 有其他日志需要记录，可以参考这个类。可吧这个类复制出来，在此基础上进行修改
 * @author 管雷鸣
 */
public class ActionLogCache {
	public static AliyunLogUtil aliyunLogUtil = null;
	static{
		ConfigManagerUtil config = ConfigManagerUtil.getSingleton("systemConfig.xml");
		//判断是否使用日志服务进行日志记录，条件便是 accessKeyId 是否为空。若为空，则不使用
		String use = config.getValue("log.logService.use");
		if(use != null && use.equals("true")){
			String keyId = config.getValue("log.logService.accessKeyId");
			String keySecret = config.getValue("log.logService.accessKeySecret");
			if(keyId == null || keyId.length() == 0){
				//取数据库的
				keyId = Global.get("ALIYUN_ACCESSKEYID");
			}
			if(keySecret == null || keySecret.length() == 0){
				//取数据库的
				keySecret = Global.get("ALIYUN_ACCESSKEYSECRET");
			}
			
			if(keyId.length() > 10){
				aliyunLogUtil = new AliyunLogUtil(config.getValue("log.logService.endpoint"),  keyId, keySecret, config.getValue("log.logService.project"), config.getValue("log.logService.logstore"));
				//开启触发日志的，其来源类及函数的记录
				aliyunLogUtil.setStackTraceDeep(4);
				aliyunLogUtil.setCacheAutoSubmit(2, 10);
				System.out.println("开启日志服务进行操作记录");
			}else{
				//此处可能是还没执行install安装
			}
			
		}else{
			//不使用日志服务进行日志记录，当然，aliyunLogUtil＝null
			System.out.println("未开启日志服务记录动作日志。若想开启，可参考网址 http://www.guanleiming.com/2348.html");
		}
	}
	

	/**
	 * 插入一条日志
	 * @param logItem 传入要保存的logItem，若为空，则会创建一个新的。此项主要为扩展使用，可自行增加其他信息记录入日志
	 * @param request HttpServletRequest
	 * @param goalid 操作的目标的id，若无，可为0，也可为空
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static synchronized void insert(LogItem logItem, HttpServletRequest request, int goalid, String action, String remark){
		if(aliyunLogUtil == null){
			//不使用日志服务，终止即可
			return;
		}
		if(logItem == null){
			logItem = aliyunLogUtil.newLogItem();
		}
		
		/*用户相关信息,只有用户登录后，才会记录用户信息*/
		User user = ShiroFunc.getUser();
		if(user != null){
			logItem.PushBack("userid", user.getId()+"");
			logItem.PushBack("username", user.getUsername());
		}
		
		/* 动作相关 */
		logItem.PushBack("goalid", goalid+"");
		logItem.PushBack("action", action);
		logItem.PushBack("remark", remark);
		
		/*浏览器自动获取的一些信息*/
		if(request != null){
			logItem.PushBack("ip", IpUtil.getIpAddress(request));
			logItem.PushBack("param", request.getQueryString());
			logItem.PushBack("url", request.getRequestURL().toString());
			logItem.PushBack("referer", request.getHeader("referer"));
			logItem.PushBack("userAgent", request.getHeader("User-Agent"));
		}
		try {
			aliyunLogUtil.cacheLog(logItem);
		} catch (LogException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param goalid 操作的目标的id，若无，可为0，也可为空
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static synchronized void insert(HttpServletRequest request, int goalid, String action, String remark){
		insert(null, request, goalid, action, remark);
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static void insert(HttpServletRequest request, String action, String remark){
		insert(null, request, 0, action, remark);
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param goalid 操作的目标的id，若无，可为0，也可为空
	 * @param action 动作的名字，如：用户登录、更改密码
	 */
	public static void insert(HttpServletRequest request, int goalid, String action){
		insert(null, request, goalid, action, "");
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param action 动作的名字，如：用户登录、更改密码
	 */
	public static void insert(HttpServletRequest request, String action){
		insert(null, request, 0, action, "");
	}
	
}
