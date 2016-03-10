<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="leftside-navigation">
	<ul class="sidebar-menu" id="nav-accordion">
		
		<!--  class="active" -->
		<shiro:hasPermission name="adminUser"> 
			<li><a href="<%=basePath %>/admin/user/list.do"><i class="fa fa-dashboard"></i><span>会员管理</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminMessage"> 
			<li><a href="<%=basePath %>/admin/message/list.do"><i class="fa fa-dashboard"></i><span>消息管理</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminLog"> 
			<li><a href="<%=basePath %>/admin/log/list.do"><i class="fa fa-dashboard"></i><span>日志管理</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminBbs"> 
			<li class="sub-menu"><a href="<%=basePath %>/admin/message/list.do"><i class="fa fa-book"></i><span>论坛管理</span></a>
			    <ul class="sub">
			        <li><a href="<%=basePath %>/admin/bbs/addClass.do">添加板块</a></li>
			        <li><a href="<%=basePath %>/admin/bbs/classList.do">板块列表</a></li>
			        <li><a href="<%=basePath %>/admin/bbs/postList.do">帖子列表</a></li>
			    </ul>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminRole"> 
			<li class="sub-menu"><a href="<%=basePath %>/admin/message/list.do"><i class="fa fa-book"></i><span>权限管理</span></a>
			    <ul class="sub">
			        <li><a href="<%=basePath %>/admin/role/addRole.do">添加角色</a></li>
			        <li><a href="<%=basePath %>/admin/role/roleList.do">角色列表</a></li>
			        <li><a href="<%=basePath %>/admin/role/permissionList.do">资源列表</a></li>
			    </ul>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="adminSystem"> 
			<li class="sub-menu"><a href="<%=basePath %>/admin/system/index.do"><i class="fa fa-book"></i><span>系统管理</span></a>
			    <ul class="sub">
			        <li><a href="<%=basePath %>/admin/system/index.do">首页</a></li>
			        <li><a href="<%=basePath %>/admin/system/generateAllCache.do">立即刷新所有缓存</a></li>
			    </ul>
			</li>
		</shiro:hasPermission>

		<li>
			<a href="#">Iworkware</a>
		</li>
	</ul>
</div>      
	