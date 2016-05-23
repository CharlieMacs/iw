<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<header id="header" class="clearfix">
	<h1>
		<img width="100%"></h1>
	<ul class="hUl clearfix">
		<li>
			<a href="<%=basePath%>friend/index.do">我的好友</a>
		</li>
		<li>
			<a href="<%=basePath %>/message/list.do?state=0&box=inbox">
			<span class="sup">${unreadMessage }</span>
				<img src="<%=basePath %>style/user/img/common/email.png" width="30px" alt="站内信（${unreadMessage }）">
				<c:if test="${unreadMessage == 0}">
					<!-- 没有站内信 -->
				</c:if>
				<c:if test="${unreadMessage == 1}">
					<!-- 有站内信，以下是条数 -->
					${unreadMessage}
				</c:if>
			</a>
		</li>
		<li>
			<a href="<%=basePath%>user/info.do">
				<img src="<%=basePath %>style/user/upload/userHead/${user.head }" width="30" height="30" alt="" class="photo">${user.nickname }</a>
			<ul class="userOperating">
				<shiro:hasPermission name="userInfo"> 
					<li>
						<a href="<%=basePath%>user/info.do">个人中心</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="userInvite"> 
					<li>
						<a href="<%=basePath%>user/invite.do">邀请注册</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="userLogout"> 
					<li>
						<a href="<%=basePath%>user/logout.do">注销账户</a>
					</li>
				</shiro:hasPermission>
			</ul>
		</li>
	</ul>
</header>
	