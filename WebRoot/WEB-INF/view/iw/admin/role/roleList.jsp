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
    	<jsp:param name="title" value="权限列表"/>
    </jsp:include>
    <script type="text/javascript">
    	//根据板块id删除帖子
    	function deleteRole(roleId){
    		//要用ajax
    		window.location="deleteRole.do?id="+roleId;
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
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="">角色列表</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;" >
                       <!-- <div class="btn-group" style="float: left;">
                            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="button">10<span class="caret"></span></button>
                            <ul role="menu" class="dropdown-menu">
                                <li><a href="#">20</a></li>
                                <li><a href="#">30</a></li>
                                <li><a href="#">40</a></li>
                                <li class="divider"></li>
                                <li><a href="all">全部</a></li>
                            </ul>
                        </div>
                        <span style="float:left;line-height:34px;margin-left:10px;">板块名：</span>
                        <div class="input-group m-bot15 " style="width:40%;float: left;">
                            <input type="text" name="querytext" class="form-control" value="请输入板块名" onfocus="javascript:if(this.value=='请输入帖子标题')this.value='';" onblur="if(!value) {value='请输入帖子额标题'; this.type='text';}">
                            <span class="input-group-btn">
                            <button class="btn btn-success" type="submit">
                            <i class="fa fa-search"></i>
                            </button>
                            </span>
                        </div> -->
                        <div style="float: right">
                            <a type="button" class="btn btn-primary" style="float: left;margin-right: 10px" href="addRole.do">
                                <i class="fa fa-plus"></i>
                            </a>
                        </div>
                    </div>
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="numeric">角色名</th>
                                    <th class="numeric">描述</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${list}" var="role">
                                	<tr>
	                                    <td>${role.id }</td>
	                                    <td class="numeric">${role.name }</td>
	                                    <td class="numeric">${role.description }</td>
	                                    <td class="numeric">
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="editRole.do?id=${role.id }">
	                                    		<i class="fa fa-pencil"></i>
	                                    	</a>
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="editRolePermission.do?roleId=${role.id }">
	                                    		权限
	                                    	</a>
	                                    	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deleteRole(${role.id });">
	                                    		<i class="fa fa-trash-o"></i>
	                                    	</button>
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
        <!-- page end-->
        </section>
    </section>
    <!--main content end-->

</section>
<!--right sidebar start-->

<jsp:include page="../../../publicPage/adminCommon/footImport.jsp"></jsp:include>  
</body>
</html>

