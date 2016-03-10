<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="${post.title }"/>
    	<jsp:param name="keywords" value="${post.title }"/>
    	<jsp:param name="description" value="${post.info }"/>
    </jsp:include>
</head>
<body>
	<article id="container">
		<jsp:include page="../common/head.jsp"></jsp:include>
		<section id="main">
			<h2>
				论坛
				<a href="addPost.do?classid=${post.classid }" class="btn fR ml10">我要发帖</a>
				<a href="list.do?classid=${post.classid }" class="btn fR">返回${postClass.name }</a>
			</h2>
			<div id="conts">
				<div class="inner topInner clearfix">
					<div class="photoBox">
						<img src="<%=basePath %>upload/userHead/${postUser.head }">
						<p class="name">${postUser.nickname }</p> 
						<p class="time"><x:time linuxTime="${post.addtime }"></x:time></p>
						<p class="category">${postClass.name }</p>
					</div>
					<div class="textBox">
						<p class="titleP">${post.title }</p>
						<p>
							${text }
						</p>
					</div>
				</div>
				
				<c:forEach items="${list}" var="postComment">
					<div class="inner clearfix">
						<div class="photoBox">
							<img src="<%=basePath %>upload/userHead/${postComment['head'] }">
							<p class="name">${postComment['nickname'] }</p>
							<p class="time"><x:time linuxTime="${postComment['addtime'] }"></x:time></p>
						</div>
						<div class="textBox">
							<p>${postComment['text'] }</p>
						</div>
					</div>
				</c:forEach>
				
				<div class="inner review clearfix">
					<div class="photoBox">
						<img src="<%=basePath %>upload/userHead/${postUser.head }">
						<p class="name">${user.nickname }</p>
					</div>
					<div class="textBox">
					<form action="addCommentSubmit.do" method="post">
						<input type="hidden" name="id" value="${post.id }" />
						<textarea rows="8" cols="8" name="text"></textarea>限制200汉字以内
						<br/>
						<ul class="btnUl">
							<li>
								<input type="submit" value="提交" class="btn btn01"></li>
							<!-- 暂时隐藏 <li>
								<input type="file" class="fileInput">
								<input type="button" value="上传图片" class="btn"></li>
							 -->
						</ul>
					</form>
						
					</div>
				</div>
			</div>
			<jsp:include page="../common/menu.jsp"></jsp:include>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>