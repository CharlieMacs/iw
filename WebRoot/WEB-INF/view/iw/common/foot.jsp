<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
</body>
<script type="text/javascript">
//form组件，开启select
layui.use(['form'], function(){
	var form = layui.form;
	
	//当编辑方式发生变动改变
	form.on('select(selectOrderBy)', function (data) {
		var selObj = document.getElementById("selectOrderBy_xnx3_id");
		var xnx3_sel_index = selObj.selectedIndex;
		if(xnx3_sel_index != defaultShow_index){
			var url = selObj.options[xnx3_sel_index].value;
			window.location.href = url;
		}
	});
});
</script>
</html>