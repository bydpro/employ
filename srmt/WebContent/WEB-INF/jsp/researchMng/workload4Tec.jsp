<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">
	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getWorkload4Tec.do",
			async : true,
			success : function(data) {
				$("#errorMsg").text(data);
			},
		});
	});
</script>
<form id="myUserForm" method="post">
	<div style="margin-bottom: 7px; margin-left: 30px; color: red;"
		id="errorMsg"></div>
</form>