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
    	<jsp:param name="title" value="设置注册用户的默认角色"/>
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
                            <a data-toggle="tab" href="">
                            	新注册会员默认赋予的角色
                            </a>
                        </li>
                    </ul>
                </header>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="" class="tab-pane active">
                        <form action="userRegRole.do" class="form-horizontal">
                            
                        		<div class="form-group">
	                                <label class="col-sm-1 control-label" style="width:100px; text-align: left; font-size: 16px; font-weight: bold;">
										选择角色
	                                </label>
	                                <div class="col-sm-9 icheck ">
										<c:forEach items="${roleList}" var="role">
		                                    <div class="minimal-purple single-row">
		                                        <div class="checkbox ">
		                                        	<input type="radio" id="subcheck" name="value" <c:if test="${role.id==system.value }"> checked</c:if> value="${role.id }"/>
		                                            <label>${role.name }</label>
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
