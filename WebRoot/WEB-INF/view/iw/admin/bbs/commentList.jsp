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
    	<jsp:param name="title" value="回帖列表"/>
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
                                <a data-toggle="tab" href="">回帖列表</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="numeric">内容</th>
                                    <th class="numeric">回复时间</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${list}" var="comment">
                                	<tr>
	                                    <td>${comment.id }</td>
	                                    <td class="numeric">${comment.text }</td>
	                                    <td><x:time linuxTime="${comment.addtime }"></x:time></td>
	                                    <td class="numeric">
	                                    	<a type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="deleteComment.do?id=${comment.id }">
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

<jsp:include page="../common/footImport.jsp"></jsp:include>  
</body>
</html>

