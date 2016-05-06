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
    	<jsp:param value="title" name="系统首页"/>
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
    <section id="main-content">
        <section class="wrapper">
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading">
                        系统首页
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    
                        
                        1.论坛板块
                        	<br/>
                        2.站内信息状态
                        <br/>
                         <a href="generateAllCache.do" class="btn btn-primary">自动生成所有缓存</a>
                        <hr/>
                        <a href="userRegRole.do" class="btn btn-primary">编辑新注册会员默认赋予的角色</a>
                    </div>
                </section>
                
                <section id="unseen">
	                <table class="table table-bordered table-striped table-condensed">
	                    <thead>
	                    <tr>
	                        <th>描述</th>
	                        <th class="numeric">值</th>
	                        <th class="numeric">操作</th>
	                    </tr>
	                    </thead>
	                    <tbody>
	                    
	                    <c:forEach items="${systemList}" var="system">
	                    	<tr>
	                         <td>${system.description }</td>
	                         <td>${system.value }</td>
	                         <td class="numeric">
	                         	<a href="editSystem.do?name=${system.name }" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deleteMessage(${message['id'] });">
	                         		<i class="fa fa-trash-o">编辑</i>
	                         	</a>
	                         </td>
	                     </tr>
	                    </c:forEach>
	                   
	                    </tbody>
	                </table>
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

