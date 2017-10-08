package com.xnx3.j2ee.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xnx3.ConfigManagerUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.net.OSSUtil;
import com.xnx3.net.OSSUtils;

/**
 * IW 快速开发底层架构的安装，比如，阿里云各种产品如OSS、日志服务等的创建等
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/install/")
public class InstallController_ extends BaseController {

	@Resource
	private SqlService sqlService;
	
	/**
	 * 安装进度
	 */
	@RequestMapping("/progress")
	public String progress(HttpServletRequest request){
		/**
		 * 
		 * OSS
		 * 		创建Bucket
		 * 日志服务
		 * 		创建project
		 * 		创建logstore
		 * 		设置索引等
		 * 发信的邮箱设置
		 * 		帐号、密码等
		 * 智能文本过滤服务
		 * 
		 * 数据库链接
		 * 		设置数据库地址、帐号、密码
		 */
		
		return "iw/login/reg";
	}
	

	/**
	 * Aliyun
	 * @throws ConfigurationException 
	 */
	@RequestMapping("/setAccessKey")
	public String setAccessKey(HttpServletRequest request) throws ConfigurationException{
		//设置阿里云的AccessKey
		ConfigManagerUtil config = ConfigManagerUtil.getSingleton("test.xml");
		config.setValue("abc", "123444");
		config.save();
		
		return "";
	}
	
	
	/**
	 * OSS创建
	 */
	@RequestMapping("/oss")
	public String oss(HttpServletRequest request){
		//判断 bucket 是否创建
		
		
		
		return "iw/login/reg";
	}
}
