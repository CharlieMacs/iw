<%@ page language="java" import="java.util.*" pageEncoding="US-ASCII"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    <a href="user/info.do">User</a>
    <br/>
    <a href="admin/bbs/postList.do">ADMIN</a>
    <a href="https://ibsbjstar.ccb.com.cn/app/ccbMain?REMARK1=weixin&CLIENTIP=58.57.74.154&BRANCHID=370000000&REMARK2=remark2&TXCODE=520100&REGINFO=xiaofeixia&CURCODE=01&GATEWAY=W0Z1&PROINFO=%u6613%u90bb%u91cc%u7f51%u94f6%u652f%u4ed8&MERCHANTID=105370783990030&ORDERID=p2743872432867328&POSID=731224913&REFERER=yilinli.com&PAYMENT=520&MAC=350654e6ef907d0f67575810d56cc44c&TYPE=1">Pay</a>
    
    
  </body>
</html>
