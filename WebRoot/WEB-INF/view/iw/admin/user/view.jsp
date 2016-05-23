<%@page import="com.xnx3.j2ee.entity.User"%>
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
	<jsp:include page="../../../publicPage/adminCommon/head.jsp">
    	<jsp:param name="title" value="查看用户资料"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>Role_role.js"></script>
    <script src="<%=basePath+Global.CACHE_FILE %>User_isfreeze.js"></script>
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
                            	查看用户资料
                            </a>
                        </li>
                    </ul>
                </header>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="" class="tab-pane active">
                        	
                        	<table class="table table-bordered table-striped table-condensed" style="font-size:14px;">
                                <tbody>
	                                <tr>
	                                    <td width="150">用户id</td>
	                                    <td>${u.id }</td>
	                                </tr>
	                                <tr>
	                                    <td>用户名</td>
	                                    <td>${u.username }</td>
	                                </tr>
	                                <tr>
	                                    <td>昵称</td>
	                                    <td>${u.nickname }</td>
	                                </tr>
	                                <tr>
	                                    <td>邮箱</td>
	                                    <td>${u.email }</td>
	                                </tr>
	                                <tr>
	                                    <td>手机号</td>
	                                    <td>${u.phone }</td>
	                                </tr>
	                                <tr>
	                                    <td>权限</td>
	                                    <td>
	                                    	<script type="text/javascript">writeName('${u.authority }');</script>
	                                    	<a type="button" class="btn btn-success btn-sm" data-toggle="modal" href="<%=basePath %>/admin/role/editUserRole.do?userid=${u.id }">
	                                    		编辑权限
	                                    	</a>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>ta的推荐人</td>
	                                    <td>${referrer }</td>
	                                </tr>
	                                <tr>
	                                    <td>注册时间</td>
	                                    <td><x:time linuxTime="${u.regtime }"></x:time></td>
	                                </tr>
	                                <tr>
	                                    <td>注册ip</td>
	                                    <td>${u.regip }</td>
	                                </tr>
	                                <tr>
	                                    <td>最后登陆时间</td>
	                                    <td><x:time linuxTime="${u.lasttime }"></x:time></td>
	                                </tr>
	                                <tr>
	                                    <td>最后登陆ip</td>
	                                    <td>${u.lastip }</td>
	                                </tr>
	                                <tr>
	                                    <td>冻结状态</td>
	                                    <td>
	                                    	当前<script type="text/javascript">document.write(isfreeze['${u.isfreeze }']);</script>
		                                    &nbsp;&nbsp;
	                                    	<c:choose>
										       <c:when test="${u.isfreeze == 0}">
										             <a type="button" class="btn btn-success btn-sm" data-toggle="modal" href="<%=basePath %>/admin/user/updateFreeze.do?id=${u.id }&isfreeze=<%=User.ISFREEZE_FREEZE %>">
										             	冻结账户
										             </a>
										       </c:when>
										       <c:otherwise>
										            <a type="button" class="btn btn-success btn-sm" data-toggle="modal" href="<%=basePath %>/admin/user/updateFreeze.do?id=${u.id }&isfreeze=<%=User.ISFREEZE_NORMAL %>">
										             	解除冻结 
										             </a>
										       </c:otherwise>
											</c:choose>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>账户余额</td>
	                                    <td>${u.money }</td>
	                                </tr>
	                                <tr>
	                                    <td>冻结金额</td>
	                                    <td>${u.freezemoney }</td>
	                                </tr>
	                                <tr>
	                                    <td><%=Global.get("CURRENCY_NAME") %></td>
	                                    <td>${u.currency }</td>
	                                </tr>
                                </tbody>
                            </table>
                        	
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
