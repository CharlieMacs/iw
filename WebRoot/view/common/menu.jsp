<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<aside id="sideBar">
	<ul class="sNavi clearfix">
		<shiro:hasPermission name="user"> 
			<li><a href="<%=basePath %>user/info.do">个人中心</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="friend"> 
			<li><a href="<%=basePath %>friend/index.do">好友</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="bbs"> 
			<li><a href="<%=basePath %>bbs/list.do">论坛</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="message"> 
			<li><a href="<%=basePath %>message/list.do">信息</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminUser"> 
			<li><a href="<%=basePath %>admin/user/list.do">管理后台</a></li>
		</shiro:hasPermission>
		
	</ul>
</aside>
<img src="<%=basePath%>style/user/img/common/arrow-left.png" width="35" id="arrowImg"></section>