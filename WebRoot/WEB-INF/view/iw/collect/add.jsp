<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../../publicPage/common/head.jsp">
    	<jsp:param name="title" value="关注列表"/>
    </jsp:include>
</head>
<body>
<article id="container">
	<jsp:include page="../../publicPage/common/top.jsp"></jsp:include>
	<section id="main">
		
		<div><h1>添加关注</h1><a href="<%=basePath %>/collect/list.do"  style="color:blue;">关注列表</a></div>
		
		<form action="addSubmit.do" method="get">
			要关注的用户id：<input type="text" name="othersid"/>
			<input type="submit" value="关注" />
		</form>
		
	</section>
	<jsp:include page="../../publicPage/common/menu.jsp"></jsp:include>
	<footer id="footer"></footer>
</article>

</body>
</html>