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
    	<jsp:param name="title" value="发信"/>
    </jsp:include>
<link href="<%=basePath %>style/user/css/friend.css" rel="stylesheet" type="text/css">
</head>
<body>
<article id="container">
	<jsp:include page="../common/top.jsp"></jsp:include>
	<section id="main">
		<form method="post" action="send.do">
			对方id：<input type="text" name="recipientid" value="" />（待优化）
			<br/>
			信息内容：<textarea name="content" rows="" cols=""></textarea>
			<br/>
			<input type="submit" value="发送" />(待优化)
		</form>
	</section>
	<jsp:include page="../common/menu.jsp"></jsp:include>
	<footer id="footer"></footer>
</article>
</body>
</html>