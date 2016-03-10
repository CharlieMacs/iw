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
	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="邀请注册"/>
    </jsp:include>
</head>
<body>

	<article id="container">
	<jsp:include page="../common/top.jsp"></jsp:include>
		
		<section id="main">
			<div class="bgBox"></div>
			<h2>
				<div id="conts">
					您的邀请注册地址：
					<br/>
					<%=basePath%>reg.do?inviteid=${user.id }
					<br/>
					将此发送给您的朋友，注册成功后，便可获得：
					
					<br/>一级下线：<%=Global.get("INVITEREG_AWARD_ONE") %><%=Global.get("CURRENCY_NAME") %>
					<br/>二级下线：<%=Global.get("INVITEREG_AWARD_TWO") %><%=Global.get("CURRENCY_NAME") %>
					<br/>三级下线：<%=Global.get("INVITEREG_AWARD_THREE") %><%=Global.get("CURRENCY_NAME") %>
					<br/>四级下线：<%=Global.get("INVITEREG_AWARD_FOUR") %><%=Global.get("CURRENCY_NAME") %>
				</div>
			<jsp:include page="../common/menu.jsp"></jsp:include>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>