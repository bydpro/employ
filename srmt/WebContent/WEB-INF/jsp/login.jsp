<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<head>
<link rel="shortcut icon" href="./image/infomation.ico" type="image/x-icon" />
<meta charset="utf-8" />
<title>登陆</title>
<link href="./css/easyui.css" rel="stylesheet" />
<link href="./css/bootstrap.min.css" rel="stylesheet" />
<link href="./css/style.css" rel="stylesheet" />
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
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
<body>

	<div class="login-form" style="margin-top: 130px;margin-left: 760px;">
		<div class="head-info">
			<label class="lbl-1"> </label> <label class="lbl-2"> </label> <label
				class="lbl-3"> </label>
		</div>
		<div class="clear"></div>
		<div class="avtar">
			<img src="./image/avtar.png" />
		</div>
		<form id="loginForm" method="post">
			<input type="text" class="text loginText inputText" id="loginId"
				placeholder="手机/邮箱/卡号" name="loginId"> <input
				type="password" id="password" placeholder="密码" name="password">
			<input type="text" class="validateCode codeText" width="38%"
				placeholder="验证码" name="validateCode" id="validateCode"> 
				<span>
					<img id="verificationCodeImg"
						src="randValidateCode.do?+'Math.random()'" title="看不清楚，换一张"
						onclick="changVaildCode()" style="width: 120px; height: 35px;" />
				</span>
		</form>
		<div class="form-group" style="margin-top: 4px;">
			<div id="loginErrorMsg" class="col-sm-8 col-sm-offset-2"
				style="color: #F00"></div>
		</div>
		<div class="signin">
			<input type="submit" value="Login" onclick="onSubmit();return false;">
		</div>
	</div>
</body>