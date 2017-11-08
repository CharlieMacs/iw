<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><%=Global.get("SITE_NAME") %></title>
</head>
<body>
	<div style="text-align: center; font-size:28px; padding-top:15%;">Welcome to use iw framework, I wish you a happy development!
	
		<%  if(!Global.databaseCreateFinish){ %>
			<div><a href="install/sql.do">数据库尚未导入！点击进入/install/sql.do 导入数据库</a></div>
		<% }  %>
	</div>
	
	
	<div style="text-align: center; padding-top:15%">
		<a href="login.do">登录后台(帐号密码都是admin)</a>
		&nbsp;|&nbsp;
		<a href="https://github.com/xnx3/iw" target="_black">GitHub开发文档</a>
	</div>
	<div style="text-align: center; padding-top:10px;">
		<a href="admin/index/index.do">管理后台 /admin/index/index.do</a>
		&nbsp;|&nbsp;
		<a href="user/index.do" target="_black">用户个人中心 /user/index.do</a>
	</div>
</body>
</html>