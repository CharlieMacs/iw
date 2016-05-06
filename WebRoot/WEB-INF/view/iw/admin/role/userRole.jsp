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
    	<jsp:param name="title" value="编辑用户[${currentUser.nickname }]权限"/>
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
                            <a data-toggle="tab" href="">编辑用户[${currentUser.nickname }]权限</a>
                        </li>
                    </ul>
                </header>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="" class="tab-pane active">
                        <form action="saveUserRole.do" method="post" class="form-horizontal">
                        	<input type="hidden" value="${currentUser.id }" name="userid" />
                        		<div class="form-group">
	                                <div class="col-sm-9 icheck ">
										<c:forEach items="${list}" var="roleMark">
		                                    <div class="minimal-purple single-row">
		                                        <div class="checkbox ">
		                                        	<input type="checkbox" id="subcheck" name="role" <c:if test="${roleMark.selected==true }"> checked</c:if> value="${roleMark.role.id }"/>
		                                            <label>${roleMark.role.name }</label>
		                                        </div>
		                                    </div>
										</c:forEach>
	                                </div>
	                            </div>
                        	
                        	
                            <div class="panel-body news-btn">
                            	<input type="submit" value="保存" class="btn btn-primary" />
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
