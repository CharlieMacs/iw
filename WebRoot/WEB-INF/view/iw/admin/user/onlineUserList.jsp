<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../../../publicPage/adminCommon/head.jsp">
    	<jsp:param name="title" value="用户列表"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>Role_role.js"></script>
    <script type="text/javascript">
    	//根据帖子id删除帖子
    	function deleteUser(userId){
    		//要用ajax
    		window.location="<%=basePath %>/admin/user/deleteUser.do?id="+userId;
    	}
    </script>
</head>
<body>

<section id="container" >
<jsp:include page="../../../publicPage/adminCommon/topHeader.jsp"></jsp:include>   
<aside>
    <div id="sidebar" class="nav-collapse">
		<jsp:include page="../../../publicPage/adminCommon/menu.jsp"></jsp:include>          
    </div>
</aside>
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="">当前在线会员列表</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
	                   
                    </div>
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th class="numeric">昵称</th>
                                    <th class="numeric">邮箱</th>
                                    <th class="numeric">手机号</th>
                                    <th class="numeric">最后上线时间</th>
                                    <th class="numeric">权限</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                
                                <c:forEach items="${list}" var="user">
                                	<tr onclick="window.location.href='view.do?id=${user.id}';" style="cursor: pointer;">
	                                    <td>${user.id }</td>
	                                    <td>${user.username }</td>
	                                    <td>${user.nickname }</td>
	                                    <td class="numeric">${user.email }</td>
	                                    <td class="numeric">${user.phone }</td>
	                                    <td class="numeric"><x:time linuxTime="${user.lasttime }"></x:time></td>
	                                    <td class="numeric"><script type="text/javascript">writeName('${user.authority }');</script></td>
	                                    <td class="numeric">
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" href="<%=basePath %>/admin/role/editUserRole.do?userid=${user.id }">
	                                    		编辑权限
	                                    	</a>
	                                    	<a type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="javascript:deleteUser(${user.id });">
	                                    		<i class="fa fa-trash-o"></i>
	                                    	</a>
	                                    </td>
	                                </tr>
                                </c:forEach>
                               
                                </tbody>
                            </table>
                        </section>
                    </div>
                </section>
            </div>
        </div>
        </section>
    </section>
    <!--main content end-->

</section>

<jsp:include page="../../../publicPage/adminCommon/footImport.jsp"></jsp:include>
</body>
</html>
