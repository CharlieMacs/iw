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
    	<jsp:param name="title" value="帖子列表"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>Bbs_postClass.js"></script>
    <script type="text/javascript">
    	function writePostClassName(postClassId){
    		document.write(postClass[postClassId]);
    	}
    	
    	//根据帖子id删除帖子
    	function deletePost(postId){
    		//要用ajax
    		window.location="<%=basePath %>/admin/bbs/deletePost.do?id="+postId;
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
                                <a data-toggle="tab" href="">帖子列表</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
                    	<form method="get">
	                        <span style="float:left;line-height:34px;margin-left:10px;">标题：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="title" class="form-control" value="<%=request.getParameter("title")==null? "":request.getParameter("title")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">时间：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;"> 
	                            <input type="text" name="addtime" class="form-control" value="<%=request.getParameter("addtime")==null? "2015-12-12 12:12:12":request.getParameter("addtime")  %>">
	                        </div>
	                        
	                        <div class="input-group m-bot15 " style="width: 100px; float: left;">
	                            <span class="input-group-btn">
	                            <input class="btn btn-success" type="submit" value="搜索">
	                            <i class="fa fa-search"></i>
	                            </button>
	                            </span>
	                        </div>
                        </form>   
                        <!-- <div style="float: right">
                            <a type="button" class="btn btn-primary" style="float: left;margin-right: 10px" href="#">
                                <i class="fa fa-plus"></i>
                            </a>
                        </div> -->
                    </div>
                        <section id="unseen">
                            <table class="table table-bordered table-striped table-condensed">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th class="numeric">所属版块</th>
                                    <th class="numeric">帖子名称</th>
                                    <th class="numeric">发帖时间</th>
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${list}" var="post">
                                	<tr>
	                                    <td>${post.id }</td>
	                                    <td class="numeric"><script type="text/javascript">writePostClassName(${post.classid });</script> </td>
	                                    <td>
	                                        <a href="<%=basePath %>/bbs/view.do?id=${post.id }" target="_black">${post.title }</a>
	                                    </td>
	                                    <td class="numeric"><x:time linuxTime="${post.addtime }"></x:time></td>
	                                    <td class="numeric">
	                                    	<!-- <a type="button" class="btn btn-success btn-sm" data-toggle="modal" target="_black" href="<%=basePath %>/bbs/view.do?id=${post.id }">
	                                    		<i class="fa fa-pencil"></i>
	                                    	</a> -->
	                                    	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deletePost(${post.id });">
	                                    		<i class="fa fa-trash-o"></i>
	                                    	</button>
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

