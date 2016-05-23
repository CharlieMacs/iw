<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../../../publicPage/adminCommon/head.jsp">
    	<jsp:param name="title" value="编辑角色权限"/>
    </jsp:include>
</head>

<body>

<section id="container" >
<!--header start-->
	<jsp:include page="../../../publicPage/adminCommon/topHeader.jsp"></jsp:include>     
<!--header end-->
<aside>
    <div id="sidebar" class="nav-collapse">
        <!-- sidebar menu start-->
        	<jsp:include page="../../../publicPage/adminCommon/menu.jsp"></jsp:include>         
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
                            <a data-toggle="tab" href="">
                            	<c:choose>
							       <c:when test="${role.id > 0}">
							              编辑角色：${role.name } 
							       </c:when>
							       <c:otherwise>
							              角色添加
							       </c:otherwise>
								</c:choose>
                            </a>
                        </li>
                    </ul>
                </header>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="" class="tab-pane active">
                        <form action="saveRole.do" class="form-horizontal">
                        	<input type="hidden" value="${role.id }" name="id">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">角色名称</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" value="${role.name }" name="name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">角色描述</label>
                                <div class="col-sm-6">
                                	<textarea rows="5" cols="65" class="form-control" name="description">${role.description }</textarea>
                               	</div>
                            </div>
                            
                            <div class="panel-body news-btn">
                            	<input type="submit" class="btn btn-primary" value="确认添加" />
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
<jsp:include page="../../../publicPage/adminCommon/footImport.jsp"></jsp:include>  
</body>
</html>
