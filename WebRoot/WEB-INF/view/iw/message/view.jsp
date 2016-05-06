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
    	<jsp:param name="title" value="阅读站内信"/>
    </jsp:include>
<link href="<%=basePath %>style/user/css/friend.css" rel="stylesheet" type="text/css">
</head>
<body>
<article id="container">
	<jsp:include page="../common/top.jsp"></jsp:include>
	<section id="main">
		<hr/>
		发信者：${messageVO.senderUser.nickname }
		<br/>
		收信者：${messageVO.recipientUser.nickname }
		<br/>
		信息发送时间：${messageVO.message.time }
		<br/>
		信息内容：${messageVO.content}
		<br/>
		<br/>
	</section>
	<footer id="footer"></footer>
</article>
</body>
</html>