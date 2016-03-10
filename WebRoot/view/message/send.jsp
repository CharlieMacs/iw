<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="写信"/>
    </jsp:include>
<link href="<%=basePath %>style/user/css/friend.css" rel="stylesheet" type="text/css">
</head>
<body>
<article id="container">
	<jsp:include page="../common/top.jsp"></jsp:include>
	<section id="main">
		${info}
	</section>
	<footer id="footer"></footer>
</article>
</body>
</html>