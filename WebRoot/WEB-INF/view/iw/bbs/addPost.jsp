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
    	<jsp:param name="title" value="发帖"/>
    </jsp:include>
</head>
<body>
	<article id="container">
		<section id="main">
			<div class="bgBox"></div>
			<h2>发帖<a href="<%=basePath%>bbs/list.do" class="btn fR">返回论坛</a></h2>
			<div id="conts">
				<dl class="clearfix">
				<form action="addPostSubmit.do" method="post">
					<input type="hidden" name="classid123" value="<%=request.getParameter("classid")%>">
					<dt>标题：</dt>
					<dd>
						<input type="text" name="title" value="" placeholder="输入您想要发表的标题" class="textInput">(2到15个汉字之间)</dd>
					<dt>论坛分类：</dt>
					<dd>
						<select name="classid">
							<c:forEach items="${postClassList}" var="postClass">
								<option value="${postClass.id }" <c:if test="${postClass.id == classid}" var="name1"> selected="selected"</c:if>>${postClass.name }</option>
							</c:forEach>
						</select>
					</dd>
					<dt>内容：</dt>
					<dd>
						<textarea name="text"></textarea>
					</dd>
					<dd>
						<input type="submit" value="发帖" class="btn btn01">
						<input type="reset" value="重置" class="btn btn01"></dd>
				</dl>
			</div>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>