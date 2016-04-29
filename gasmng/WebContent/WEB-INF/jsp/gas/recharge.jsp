<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="./css/jquery-labelauty.css" rel="stylesheet" />
<script type="text/javascript" src="./js/jquery-labelauty.js"></script>
<style type="text/css">
ul{list-style-type: none;}
li{display: inline-block;}
li{margin: 10px 0;}
input.labelauty + label{font:12px "Microsoft Yahei";}
</style>
<script type="text/javascript">

	$(function() {
		$(':input').labelauty();

		$.ajax({
			type : "POST",
			dataType : "json",
			url : "userMng/getMyMoney.do",
			async : true,
			success : function(data) {
				$("#myMoney").html("￥ " + data.money);
			},
		})

	});

	function getSelVal() {
		var value = "";
		$('input[name="radio"]:checked').each(function() {
			value = $(this).val();
		});
		$("#reMoney").val(value);
		$("#reMoneyLable").html("￥ " + value + ".00");
	}


	function reMoney() {
		var money = $("#reMoney").val();
		if (money) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "userMng/reMoney.do",
				async : true,
				 data: {money:money},
				success : function(data) {
					$("#myMoney").html("￥ " + data.money+ ".00");
					$.messager.alert('提示', '充值成功!');
				},
			})

		} else {
			$.messager.alert('提示', '请选择充值金额!');
			return false;
		}
	}
</script>
<form id="myUserForm" method="post">
	<div style="margin-bottom: 7px;">
		<label >当前可用余额：</label> <label id="myMoney" style="color: red;"></label> 
	</div>
	<div style="margin-bottom: 7px;">
		<h4>请选择充值金额:</h4>
		<ul class="dowebok" onclick="getSelVal()">
			<li><input type="radio" name="radio" data-labelauty="20" value="20"  ></li>
			<li><input type="radio" name="radio" data-labelauty="50" value="50" > </li>
			<li><input type="radio" name="radio" data-labelauty="100" value="100" ></li>
			<br>
			<li><input type="radio" name="radio" data-labelauty="200" value="200" ></li>
			<li><input type="radio" name="radio" data-labelauty="300" value="300" ></li>
			<li><input type="radio" name="radio" data-labelauty="500" value="500"></li>
		</ul>
	</div>
	<div style="margin-bottom: 7px;">
		<label>充值金额：</label> 
		<label id="reMoneyLable" style="color: red;"></label>
		<input
			name="reMoney" class="easyui-validatebox"
			style="width: 200px; height: 30px;"  id="reMoney" hidden="true">
	</div>
	<div style="margin-bottom: 7px;" align="left">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="reMoney()" style=" width: 200px;">立即充值</a>
	</div>
</form>
