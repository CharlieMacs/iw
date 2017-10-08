<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>无权访问</title>
  </head>
  
  <body>
   无权访问！
    <br/>
    <a href="<%=basePath %>login.do" style="color:blue">登陆</a>
    <br/>
    <a href="<%=basePath %>user/login.do" style="color:blue">退出登陆</a>
    <br/>
    <a href="<%=basePath %>user/info.do" style="color:blue">个人中心</a>
  </body>
</html>
