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
	<jsp:include page="../../publicPage/common/head.jsp">
    	<jsp:param name="title" value="帖子列表"/>
    </jsp:include>
	<link href="<%=basePath %>style/user/css/forum.css" rel="stylesheet" type="text/css">
</head>
<body>
	<article id="container">
		<jsp:include page="../../publicPage/common/head.jsp"></jsp:include>
		<section id="main">
			<div class="bgBox"></div>
			<h2>
				<img src="<%=basePath %>style/user/img/common/star01.png" alt="">论坛${tt }<a href='addPost.do?classid=<%=request.getParameter("classid") %>' class="btn fR">我要发帖</a></h2>
			<div id="conts" class="clearfix">
				<div class="lBox">
					<ul class="borderUl">
						<li>
							<a href="list.do?classid=1">一般论坛</a>
						</li>
						<li>
							<a href="list.do?classid=2">我要报bug</a>
						</li>
						<li>
							<a href="list.do?classid=3">我要提需求</a>
						</li>
						<li>
							<a href="list.do?classid=4">常见问题</a>
						</li>
						<li>
							<a href="list.do?classid=5">功能更新</a>
						</li>
						<li>
							<a href="list.do?classid=6">使用教程</a>
						</li>
					</ul>
				</div>
				<div class="rBox">
					<ul class="borderUl clearfix">
					
					<c:forEach items="${list}" var="post">
						<li>
							<img src="<%=basePath+Global.USER_HEAD_FILE %>${post['head'] }" class="photo" alt="${post['nickname'] }">
							<div class="textBox">
									<p class="title">
										<a href="view.do?id=${post['id'] }">${post['title'] }</a>
									</p>
									<p><a href="view.do?id=${post['id'] }">${post['info'] }</a></p>
							</div>
							<p class="numP">${post['view'] }</p>
						</li>
					</c:forEach>
						
					</ul>
					
				    <!-- 通用分页跳转 -->
                    <jsp:include page="../../publicPage/common/page.jsp">
                    	<jsp:param name="page" value="${page }"/>
                    </jsp:include>
				</div>
			</div>
			<jsp:include page="../../publicPage/common/menu.jsp"></jsp:include>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>