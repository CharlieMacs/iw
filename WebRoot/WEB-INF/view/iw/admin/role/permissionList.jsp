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
    	<jsp:param name="title" value="资源列表"/>
    </jsp:include>
    <script type="text/javascript">
    	//根据板块id删除帖子
    	function deletePermission(permissionId){
    		//要用ajax
    		window.location="deletePermission.do?id="+permissionId;
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
		<!-- sidebar menu end -->
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
                                <a data-toggle="tab" href="">资源列表</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;" >
                        <form method="get">
	                        <span style="float:left;line-height:34px;margin-left:10px;">名字：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="name" class="form-control" value="<%=request.getParameter("name")==null? "":request.getParameter("name")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">percode：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;"> 
	                            <input type="text" name="percode" class="form-control" value="<%=request.getParameter("percode")==null? "":request.getParameter("percode")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">描述：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="description" class="form-control" value="<%=request.getParameter("description")==null? "":request.getParameter("description")  %>">
	                        </div>
	                        
	                        <div class="input-group m-bot15 " style="width: 100px; float: left;">
	                            <span class="input-group-btn">
	                            <input class="btn btn-success" type="submit" value="搜索">
	                            <i class="fa fa-search"></i>
	                            </button>
	                            </span>
	                        </div>
                        </form>   
                        
                        <div style="float: right">
                            <a type="button" class="btn btn-primary" style="float: left;margin-right: 10px" href="addPermission.do?parentId=0">
                                <i class="fa fa-plus"></i>
                            </a>
                        </div>
                    </div>
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th class="numeric">顶级的</th>
                                    <th class="numeric">菜单名称</th>
                                    <th class="numeric">描述</th>
                                    <th class="numeric">URL</th>
                                    <th class="numeric">percode</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${list}" var="permissionTree">
                                	<tr style="font-weight: bold;color: blue;">
	                                    <td>+/-</td>
	                                    <td class="numeric">${permissionTree.permissionMark.permission.name }</td>
	                                    <td class="numeric">${permissionTree.permissionMark.permission.description }</td>
	                                    <td class="numeric">${permissionTree.permissionMark.permission.url }</td>
	                                    <td class="numeric">${permissionTree.permissionMark.permission.percode }</td>
	                                    <td class="numeric">
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="addPermission.do?parentId=${permissionTree.permissionMark.permission.id }">
	                                    		+
	                                    	</a>
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="editPermission.do?id=${permissionTree.permissionMark.permission.id }">
	                                    		<i class="fa fa-pencil"></i>
	                                    	</a>
	                                    	
	                                    	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deletePermission(${permissionTree.permissionMark.permission.id });">
	                                    		<i class="fa fa-trash-o"></i>
	                                    	</button>
	                                    </td>
	                                </tr>
	                                
	                                <!--二级菜单权限 -->
	                                <c:forEach items="${permissionTree.list }" var="permissionMark">
	                                	<tr>
		                                    <td>&nbsp;</td>
		                                    <td class="numeric">${permissionMark.permission.name }</td>
		                                    <td class="numeric">${permissionMark.permission.description }</td>
		                                    <td class="numeric">${permissionMark.permission.url }</td>
		                                    <td class="numeric">${permissionMark.permission.percode }</td>
		                                    <td class="numeric">
		                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="editPermission.do?id=${permissionMark.permission.id }">
		                                    		<i class="fa fa-pencil"></i>
		                                    	</a>
		                                    	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deletePermission(${permissionMark.permission.id });">
		                                    		<i class="fa fa-trash-o"></i>
		                                    	</button>
		                                    </td>
		                                </tr>
									</c:forEach>
	                                
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

<jsp:include page="../common/footImport.jsp"></jsp:include>  
</body>
</html>

