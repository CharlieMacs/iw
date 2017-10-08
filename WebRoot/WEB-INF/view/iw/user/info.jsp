<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../../publicPage/common/head.jsp">
    	<jsp:param name="title" value="个人中心"/>
    </jsp:include>
	
<script type="text/javascript">
$(function(){
	$(".uppass").click(function(){
		var passold = $(".passold");
		var passnew = $(".passnew");
		var passren = $(".passren");

		if(passold.val()==''){
			alert('原先密码不能为空');
			uname.focus();
			return;
		}
		if(passnew.val()==''){
			alert('新密码不能为空');
			uname.focus();
			return;
		}
		if(passren.val()==''){
			alert('请再次确认密码');
			uname.focus();
			return;
		}
		if(passren.val()!=passnew.val()){
			alert('两次密码不一致');
			uname.focus();
			return;
		}
		   	$.post('<%=basePath %>user/updatePassword.do', {oldPassword : passold.val(),newPassword : passnew.val(),enterPassword : passren.val()}, function (data) {

   				if (data.result==1) {
   					alert('修改密码成功');
   				} else {
   					alert(data.info);
   				}
   			}, 'json');

	});
});
</script>
</head>
<body>

	<article id="container">
		
		<div style="width:100%; height:200px;">
		
		
		</div>
		
		<section id="main">
			<div class="bgBox"></div>
			<h2>用户中心</h2>
			<div id="conts">
				<ul class="naviUl clearfix">
					<li class="on">
						<a href="../user/info.do">用户中心</a>
					</li>
					<li>
						<a href="../friend/index.do">好友管理</a>
					</li>
				</ul>
				<h3>修改个人资料</h3>
				<div class="innerBox">
					<div class="clearfix">
						<div class="photo">
							<img src="<%=basePath %>upload/userHead/${user.head }" alt=""></div>
						<div class="textBox">
							<p>你可以选择png/jpg图片（180*180）作为头像</p>
						</div>
					</div>
					<div class="changePhoto clearfix">
						<form action="uploadHead.do" enctype="multipart/form-data" method="post">
							<input type="file" value="" name="head" >
							<input type="submit" value="保存">
							<!-- <span>修改头像</span> -->
						</form>
						<input type="submit" class="btn" style="margin-top: 3px;">
					</div>
				</div>
				<div class="borderBox">
					<table cellpadding="0" cellspacing="0">
						<col width="20%">
						<tbody>
							<form method="post" action="updateNickName.do">
								<tr>
									<th>昵称：</th>
									<td>
										<input type="text" name="nickname" value="${user.nickname}" class="textInput"></td>
								</tr>
								<tr>
									<td colspan="2" class="taCenter">
										<input type="submit" value="保存" class="btn"></td>
								</tr>
							</form>
						</tbody>
					</table>
				</div>
				<div class="borderBox">
					<p>修改密码</p>
					<form>
						<table cellpadding="0" cellspacing="0">
							<col width="20%">
							<tbody>
								<tr>
									<th>原密码：</th>
									<td>
										<input type="password" name="oldPassword" value="" class="textInput passold"></td>
								</tr>
								<tr>
									<th>新密码：</th>
									<td>
										<input type="password" name="newPassword" value="" class="textInput passnew"></td>
								</tr>
								<tr>
									<th>重复新密码：</th>
									<td>
										<input type="password" name="enterPassword" value="" class="textInput passren"></td>
								</tr>
								<tr>
									<td colspan="2" class="taCenter">
										<input type="button" value="保存" class="btn uppass"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div class="borderBox">
					<!-- <p>修改邮箱地址</p> -->
					<p>
						当前邮箱地址为:
						<span class="email">${user.email}</span>
					</p>
				</div>
			</div>
			</section>
		<footer id="footer"></footer>
	</article>
</body>
</html>