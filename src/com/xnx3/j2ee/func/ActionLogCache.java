package com.xnx3.j2ee.func;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.xnx3.ConfigManagerUtil;
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
		aliyunLogUtil = new AliyunLogUtil(config.getValue("log.logService.endpoint"),  config.getValue("log.logService.accessKeyId"), config.getValue("log.logService.accessKeySecret"), config.getValue("log.logService.project"), config.getValue("log.logService.logstore"));
		
		//开启触发日志的，其来源类及函数的记录
		aliyunLogUtil.setStackTraceDeep(4);
		
		aliyunLogUtil.setCacheAutoSubmit(2, 10);
	}
	

	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param goalid 操作的目标的id，若无，可为0，也可为空
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static void insert(HttpServletRequest request, int goalid, String action, String remark){
		LogItem logItem = aliyunLogUtil.newLogItem();
		
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
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static void insert(HttpServletRequest request, String action, String remark){
		insert(request, 0, action, remark);
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param goalid 操作的目标的id，若无，可为0，也可为空
	 * @param action 动作的名字，如：用户登录、更改密码
	 */
	public static void insert(HttpServletRequest request, int goalid, String action){
		insert(request, goalid, action, "");
	}
	
	/**
	 * 插入一条日志
	 * @param request HttpServletRequest
	 * @param action 动作的名字，如：用户登录、更改密码
	 * @param remark 动作的描述，如用户将名字张三改为李四
	 */
	public static void insert(HttpServletRequest request, String action){
		insert(request, 0, action, "");
	}
	
}
