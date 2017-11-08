package com.xnx3.j2ee.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.Connection;
import com.xnx3.ConfigManagerUtil;
import com.xnx3.file.FileUtil;
import com.xnx3.j2ee.service.SqlService;

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
	
	/**
	 * SQL导入
	 */
	@RequestMapping("/sql")
	public String sql(HttpServletRequest request){
		//判断 bucket 是否创建
		final String sql = FileUtil.read("/Users/apple/git/iw/iw.sql", "UTF-8");
//		String sql1 = "CREATE TABLE `area` (  `id` int(11) NOT NULL,  `province` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省份',  `city` char(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '城市',  `district` char(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',  `longitude` float(5,2) DEFAULT NULL COMMENT '经度',  `latitude` float(5,2) DEFAULT NULL COMMENT '纬度',  PRIMARY KEY (`id`),  KEY `province` (`province`,`city`,`district`,`longitude`,`latitude`)) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='地区，省市区表';";
		
		
		
		Session session = sqlService.getCurrentSession();
		//通过session获取事务
        Transaction ts = session.beginTransaction();
		
		session.doWork(new Work() {
			@Override
			public void execute(java.sql.Connection connection) throws SQLException {
				String s[] = sql.split("-----");
				for (int i = 0; i < s.length; i++) {
					String sqlExecute = s[i].trim();
					sqlExecute = sqlExecute.replaceAll("\t", "").replaceAll("\n", "");
					System.out.println(sqlExecute);
				
					try {
	                    PreparedStatement stmt = connection.prepareStatement(sqlExecute);
	                    stmt.execute(sqlExecute);
	                    ts.commit();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    ts.rollback();
	                }
					
				}
			}
		});
		
		
		return "iw/login/reg";
	}
}
