<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!--right sidebar end-->
<!-- Placed js at the end of the document so the pages load faster -->

<!--Core js-->
<script src="<%=basePath %>style/admin/js/jquery.js"></script>
<script src="<%=basePath %>style/admin/bs3/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="<%=basePath %>style/admin/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="<%=basePath %>style/admin/js/jquery.scrollTo.min.js"></script>
<script src="<%=basePath %>style/admin/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.js"></script>
<script src="<%=basePath %>style/admin/js/jquery.nicescroll.js"></script>
<!--Easy Pie Chart-->
<script src="<%=basePath %>style/admin/js/easypiechart/jquery.easypiechart.js"></script>
<!--Sparkline Chart-->
<script src="<%=basePath %>style/admin/js/sparkline/jquery.sparkline.js"></script>
<!--jQuery Flot Chart-->
<script src="<%=basePath %>style/admin/js/flot-chart/jquery.flot.js"></script>
<script src="<%=basePath %>style/admin/js/flot-chart/jquery.flot.tooltip.min.js"></script>
<script src="<%=basePath %>style/admin/js/flot-chart/jquery.flot.resize.js"></script>
<script src="<%=basePath %>style/admin/js/flot-chart/jquery.flot.pie.resize.js"></script>

<!--common script init for all pages-->
<script src="<%=basePath %>style/admin/js/scripts.js"></script>
<script src="<%=basePath %>style/admin/js/toggle-init.js"></script>
<script src="<%=basePath %>style/admin/js/advanced-form.js"></script>