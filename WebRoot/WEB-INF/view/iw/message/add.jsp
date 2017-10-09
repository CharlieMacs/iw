<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="发送站内信息"/>
</jsp:include>

<article id="container">
	<section id="main">
		<form method="post" action="send.do">
			对方id：<input type="text" name="recipientid" value="" />（待优化）
			<br/>
			信息内容：<textarea name="content" rows="" cols=""></textarea>
			<br/>
			<input type="submit" value="发送" />(待优化)
		</form>
	</section>
	<footer id="footer"></footer>
</article>
</body>
</html>