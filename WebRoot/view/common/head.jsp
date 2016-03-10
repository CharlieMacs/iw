<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//标题
String title = request.getParameter("title");
if(title == null ||title.length()==0 || title.equals("null")){
	title = Global.get("SITE_NAME");
}else{
	title=title+"_"+Global.get("SITE_NAME");
}

//关键字
String keywords = request.getParameter("keywords");
if(keywords == null || keywords.length()==0 || keywords.equals("null")){
	keywords = Global.get("SITE_KEYWORDS");
}

//描述
String description = request.getParameter("description");
if(description == null || description.length()==0 || description.equals("null")){
	description = Global.get("SITE_DESCRIPTION");
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=title %></title>
<meta name="keywords" content="<%=keywords %>" />
<meta name="description" content="<%=description %>" />
<meta http-equiv="content-script-type" content="text/javascript">
<meta http-equiv="content-style-type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=basePath %>style/user/css/common/layout.css" rel="stylesheet" type="text/css">
<link href="<%=basePath %>style/user/css/common/general.css" rel="stylesheet" type="text/css">
<script src="<%=basePath %>style/user/js/jquery.js"></script>
<script src="<%=basePath %>style/user/js/common.js"></script>
<!--[if IE]>
<script src="<%=basePath %>style/user/js/html5.js"></script>
<![endif]-->