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
	<jsp:include page="../common/head.jsp">
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
<!--header start-->
<jsp:include page="../common/topHeader.jsp"></jsp:include>   
<!--header end-->
<aside>
    <div id="sidebar" class="nav-collapse">
        <!-- sidebar menu start-->
			<jsp:include page="../common/menu.jsp"></jsp:include>          
		<!-- sidebar menu end-->
    </div>
</aside>
<!--sidebar end-->
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="">会员管理</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
                       <!-- <div class="btn-group" style="float: left;">
                            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="button">10<span class="caret"></span></button>
                            <ul role="menu" class="dropdown-menu">
                                <li><a href="#">20</a></li>
                                <li><a href="#">30</a></li>
                                <li><a href="#">40</a></li>
                                <li class="divider"></li>
                                <li><a href="all">全部</a></li>
                            </ul>
                        </div> -->
	                    <form method="get">
	                        <span style="float:left;line-height:34px;margin-left:10px;">会员名称：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="username" class="form-control" value="<%=request.getParameter("username")==null? "":request.getParameter("username")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">昵称：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;"> 
	                            <input type="text" name="nickname" class="form-control" value="<%=request.getParameter("nickname")==null? "":request.getParameter("nickname")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">注册邮箱：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="email" class="form-control" value="<%=request.getParameter("email")==null? "":request.getParameter("email")  %>">
	                        </div>
	                        
	                        <div class="input-group m-bot15 " style="width: 100px; float: left;">
	                            <span class="input-group-btn">
	                            <input class="btn btn-success" type="submit" value="搜索">
	                            <i class="fa fa-search"></i>
	                            </button>
	                            </span>
	                        </div>
                        </form>     
                        <!-- 
                        <span style="float:left;line-height:34px;margin-left:10px;">手机号码：</span>
                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
                            <input type="text" name="phone" class="form-control" value="请输入手机号码" onfocus="javascript:if(this.value=='请输入手机号码')this.value='';" onblur="if(!value) {value='请输入手机号码'; this.type='text';}">
                            <span class="input-group-btn">
                            <button class="btn btn-success" type="submit">
                            <i class="fa fa-search"></i>
                            </button>
                            </span>
                        </div>
                        
                        <div style="float: right">
                            <div class="btn-group" style="float: left;margin-right:10px;">
                                <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="submit">全部<span class="caret"></span></button>
                                <ul role="menu" class="dropdown-menu" style="min-width:120px;">
                                    <li><a href="#">VIP</a></li>
                                    <li><a href="#">普通</a></li>
                                    <li><a href="#">正常</a></li>
                                    <li><a href="#">冻结</a></li>
                                </ul>
                            </div>
                            <a type="button" class="btn btn-primary" style="float: left;margin-right: 10px" href="#">
                                <i class="fa fa-plus"></i>
                            </a>
                        </div>
                         -->
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
                                    <!-- 
                                    	<th class="numeric">状态</th>
                                    	<th class="numeric">状态</th> 
                                    -->
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
                        <!-- 通用分页跳转 -->
                        <jsp:include page="../common/page.jsp">
                        	<jsp:param name="page" value="${page }"/>
                        </jsp:include>
                    </div>
                </section>
                
            </div>
        </div>
        <!-- page end-->
        </section>
    </section>
    <!--main content end-->

</section>
<!--right sidebar start-->


<!--right sidebar end-->
<!-- Placed js at the end of the document so the pages load faster -->

<jsp:include page="../common/footImport.jsp"></jsp:include>
</body>
</html>
