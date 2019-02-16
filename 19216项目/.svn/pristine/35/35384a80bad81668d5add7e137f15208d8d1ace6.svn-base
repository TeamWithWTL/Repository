<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.jcwx.utils.ProjectUtils"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<% String projectName = ProjectUtils.getSysCfg("projectName");%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title><%=projectName%></title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"	href="${ctxPath}/assets/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"  href="${ctxPath}/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"  href="${ctxPath}/assets/css/login2.css" />
<link rel="icon" href="${ctxPath}/assets/img/head_logo.png" type="image/x-icon" />
</head>
<body>
	
	<div class="download-app"><a class="qrcode"></a></div>
		<div class="mask">
			<img src="${ctxPath}/assets/images/112_erwm.png" style="width:220px;height:220px;"/>
			
			
		</div>
		
		<div class="warpper">
			<div class="container">
				<div class="box-content">
					<div class="logo">
						<img src="${ctxPath}/assets/images/login-logo2.png" width="530"/>
					</div>
					<div class="login-box">
						<h2>用户登录</h2>
						<form  id="loginForm" name="myform">
							<span class="username">
								<input type="text" class="form-control" id="signin-acccode" name="accCode" placeholder="用户名" />
							</span>
							<span class="password">
								<input type="password" class="form-control" id="signin-password" name="pwd" placeholder="密码" />
							</span>
							<span class="vrcode">
								<input type="text" class="form-control" id="authCode" name="authCode" placeholder="验证码" />
								<img src="${ctxPath}/codeImage"
							id="codeImage" onclick="changeCode()" title="点击更换验证码"/>
							</span>
							<button type="button" class="btn btn-submit" id="loginBtn">登录</button>
						</form>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>



	<input type="hidden" id="basePath" value="${ctxPath}" />

	<script src="${ctxPath}/assets/vendor/jquery/jquery.min.js"></script>
	<script src="${ctxPath}/assets/vendor/layer/layer.js"></script>
	<script src="${ctxPath}/assets/scripts/login.js"></script>
	<script type="text/javascript">
			$(".qrcode").click(function(){
				$(".mask").fadeIn(200);
			});
			$(".mask").click(function(){
				$(this).fadeOut(200);
			});
		</script>
</body>
</html>