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
    	<jsp:param name="title" value="登录"/>
    </jsp:include>
    <link href="<%=basePath %>style/user/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
	<article id="container">
		<section class="section"><h1><%=Global.get("SITE_NAME") %></h1>
			<div class="loginBox">
				<h2><span>用户登录</span></h2>
				<c:if test="${error != null}">
					<h1><span>出错：${error }</span></h1>
				</c:if>
				<form action="<%=basePath %>loginSubmit.do" method="post" id="form">	
					<ul><li>
							<input type="text" placeholder="用户名/邮箱" class="textInput username" name="username" value="${username }" >
							<span class="icon">用户名/邮箱</span>
						</li>
						<li>
							<input type="password" placeholder="请输入密码" class="textInput password" name="password" value="${password }">
							<span class="icon">密码</span>
						</li>
						<!-- <li>
							<input type="checkbox" name="rememberMe" value="true" />自动登录
						</li>
						 -->
						<!-- 
							<dt>验证码：</dt>
							<dd><input type="text" class="code textInput" placeholder="验证码"></dd>
						-->
						<li><input class="btn" type="submit" value="登 陆"></li>
						<li><a class="btn" href="reg.do">我要注册</a></li>
					</ul>
					<div class="btnBox"></div>
				</form>
			</div>
		</section>
	</article>
</body>
<script type="text/javascript">
if(${autoLogin}){
	$('form').submit();
}
</script>
</html>