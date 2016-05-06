<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="编辑资源"/>
    </jsp:include>
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
    <section id="main-content" >
        <section class="wrapper">


            <div class="row">

            <div class="col-lg-12">
            <!--tab nav start-->
            <section class="panel">
                <header class="panel-heading tab-bg-dark-navy-blue ">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <c:choose>
						       <c:when test="${permission.id > 0}">
						              编辑资源：${permission.name } 
						       </c:when>
						       <c:otherwise>
						              资源添加
						       </c:otherwise>
							</c:choose>
                        </li>
                    </ul>
                </header>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="" class="tab-pane active">
                        <form action="savePermission.do" class="form-horizontal">
                        	<input type="hidden" value="${permission.id }" name="id">
                        	<div class="form-group">
                                <label class="col-sm-2 control-label">所属上级资源</label>
                                <div class="col-sm-6">
                                	${parentPermissionDescription }
                                    <input type="hidden" class="form-control" value="${permission.parentId }" name="parentId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">资源菜单名称</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${permission.name }" name="name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">描述备注</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${permission.description }" name="description">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">percode</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${permission.percode }" name="percode">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">URL</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${permission.url }" name="url">
                                </div>
                            </div>
                            
                            <div class="panel-body news-btn">
                            	<input type="submit" class="btn btn-primary" value="保存" />
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </section>

            </div>

            </div>


        </section>
    </section>
    <!--main content end-->

</section>

<!-- Placed js at the end of the document so the pages load faster -->
<jsp:include page="../common/footImport.jsp"></jsp:include>  
</body>
</html>
