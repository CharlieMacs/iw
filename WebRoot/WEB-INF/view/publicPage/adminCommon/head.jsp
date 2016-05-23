<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//标题
String title = request.getParameter("title");
if(title == null ){
	title = Global.get("SITE_NAME");
}else{
	title=title+"_"+Global.get("SITE_NAME");
}

//关键字
String keywords = request.getParameter("keywords");
if(keywords == null ){
	keywords = Global.get("SITE_KEYWORDS");
}

//描述
String description = request.getParameter("description");
if(description == null ){
	description = Global.get("SITE_DESCRIPTION");
}
%>
<meta charset="utf-8">
<title><%=title %></title>
<meta name="keywords" content="<%=keywords %>" />
<meta name="description" content="<%=description %>" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="ThemeBucket">
<link rel="shortcut icon" href="<%=basePath %>style/admin/images/favicon.png">

<!--Core CSS -->
<link href="<%=basePath %>style/admin/bs3/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath %>style/admin/css/bootstrap-reset.css" rel="stylesheet">
<link href="<%=basePath %>style/admin/font-awesome/css/font-awesome.css" rel="stylesheet" />

<!--responsive table-->
<link href="<%=basePath %>style/admin/css/table-responsive.css" rel="stylesheet" />

<!-- Custom styles for this template -->
<link href="<%=basePath %>style/admin/css/style.css" rel="stylesheet">
<link href="<%=basePath %>style/admin/css/style-responsive.css" rel="stylesheet" />
<link href="<%=basePath %>style/admin/css/iworkware.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]>
<script src="<%=basePath %>style/admin/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->
<script src="<%=basePath %>style/admin/js/iw_list.js"></script>