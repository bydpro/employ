<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<head>
<meta charset="utf-8" />
<title>登陆</title>
<link href="./css/easyui.css" rel="stylesheet" />
<link href="./css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/json2.js"></script>
<script type="text/javascript">
	function changVaildCode() {
		$("#verificationCodeImg").attr("src",
				"randValidateCode.do?" + Math.random());
	}

	function showErrorMsg(msg) {
		$("#loginErrorMsg").text(msg);
		$("#loginErrorMsg").show("slow");
	}
	//隐藏错误信息
	function hiddenErrorMsg() {
		$("#loginErrorMsg").text("");
		$("#loginErrorMsg").hide();
	}

	$(function() {
		hiddenErrorMsg();
	})

	function onSubmit() {
		var loginId = $("#loginId").val();
		if (loginId == "") {
			showErrorMsg("用户名不能为空!");
			$("#loginId").focus();
			return;
		}

		var pwd = $("#password").val();
		if (pwd == "") {
			showErrorMsg("密码不能为空!");
			$("#password").focus();
			return;
		}

		var validateCode = $("#validateCode").val();
		if (validateCode == "") {
			showErrorMsg("验证码不能为空!");
			$("#validateCode").focus();
			return;
		}

		$.ajax({
			type : "POST",
			dataType : "json",
			url : "loginIn.do",
			data : $('#loginForm').serialize(),
			async : true,
			success : function(flag) {
				if (flag.msg == "1") {
					var jsp = "enterMainPage.do";
					window.location.href = jsp;
				} else if (flag.msg == "0") {
					showErrorMsg("用户名或密码错误！");
					$("#loginId").focus();
				} else {
					showErrorMsg(flag.msg);
				}
			},
		})
	}
</script>
</head>
<style type="text/css">
body {
	background-image: url("./image/login2.jpg");
	background-position: 50% 30%;
	background-repeat: no-repeat;
}
</style>
<body>
	<div style="display: inline;">
		<div style="margin-top: 250px; margin-left: 150px; float: left;">
			<img alt="" src="./image/login1.jpg">
		</div>
		<div style="margin-top: 280px; width: 400px; float: left;border: 2px;'">
			<form class="form-horizontal" method="post" id="loginForm">
				<div class="form-group">
					<label for="loginId" class="col-sm-3 control-label">用户名</label>
					<div class="col-sm-7">
						<input type="text" class="form-control" id="loginId"
							placeholder="手机/邮箱/用户名" name="loginId">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-3 control-label">密码</label>
					<div class="col-sm-7">
						<input type="password" class="form-control" id="password"
							placeholder="密码" name="password">
					</div>
				</div>
				<div class="form-group">
					<label for="validateCode" class="col-sm-3 control-label">验证码</label>
					<div class="col-sm-4 ">
						<input id="validateCode" type="text" placeholder="验证码"
							class="form-control" name="validateCode">
					</div>
					<span> <img id="verificationCodeImg"
						src="randValidateCode.do?+'Math.random()'" title="看不清楚，换一张"
						onclick="changVaildCode()" style="width: 90px; height: 30px" />
					</span>
				</div>
				<div class="form-group">
					<div id="loginErrorMsg" class="col-sm-7 col-sm-offset-3"
						style="color: #F00"></div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-8">
						<button type="submit" class="btn btn-primary"
							onclick="onSubmit();return false;" style="width: 100px;">登陆</button>
						<button type="reset" class="btn btn-primary" style="width: 100px;">重置</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>