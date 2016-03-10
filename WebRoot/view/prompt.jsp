<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String redirectUrl = request.getAttribute("redirectUrl").toString();
if(redirectUrl.indexOf("http")>0){
}else if(redirectUrl.indexOf("avascript")>0){
}else{
	redirectUrl = basePath+redirectUrl;
}

request.setAttribute("success", Global.PROMPT_STATE_SUCCESS);
request.setAttribute("error", Global.PROMPT_STATE_ERROR);
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="common/head.jsp">
    	<jsp:param name="title" value="提示"/>
    </jsp:include>  
</head>
<body>
	<article id="container">
		<jsp:include page="common/top.jsp"></jsp:include>
		<section id="main">
			<div class="bgBox"></div>
			<h2>
			<!-- 信息提示页面，执行完一个操作后提示是成功还是失败，然后等待5秒自动跳转 -->
				<h1>提示信息：${info }</h1>
				<h3>提示状态：
					<c:choose>
				       <c:when test="${state == success}">
				              成功提示 
				       </c:when>
				       <c:when test="${state == error}">
				              错误提示 
				       </c:when>
				       <c:otherwise>
				              不知道啥类型提示
				       </c:otherwise>
					</c:choose>
				</h3>
			<h2><a href="<%=redirectUrl %>" style="color:blue;">点击跳转</a></h2>
			<jsp:include page="common/menu.jsp"></jsp:include>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>