<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">

	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getProjectScore.do",
			async : true,
			success : function(data) {
				$('#projectScoreForm').form('load', data);

			},
		});
	});
	
	function saveThesisScoore() {
		$('#projectScoreForm').form('submit', {
			url : "research/saveProjectScore.do",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.alert({
						title : 'Error',
						msg : result.msg
					});
				} else {
					$.messager.alert({
						msg : '保存成功'
					});
				}
			}
		});
	}
</script>
<div style="margin-left: 200px;">
<form id="projectScoreForm" method="post">
	<div style="margin-bottom: 7px;margin-top: 80px;">
		<label style="width: 160px;">国家级项目</label>
		<label>调节系数:</label>
		<input class="easyui-numberbox"  data-options="precision:2,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="guoK"> 
		<label>到位经费</label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="guoFund"><label style="height: 30px;"> / </label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="guoFundLast">		
	</div>
	<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">部省级项目</label>
		<label>调节系数:</label>
		<input class="easyui-numberbox"  data-options="precision:2,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenK"> 
		<label>到位经费</label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenFund"><label style="height: 30px;"> / </label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenFundLast">		
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 160px;">其它纵向项目和横向项目</label>
		<label>调节系数:</label>
		<input class="easyui-numberbox"  data-options="precision:2,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="otherK"> 
		<label>到位经费</label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="otherFund"><label style="height: 30px;"> / </label>
		<input class="easyui-numberbox"  data-options="precision:0,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="otherFundLast">		
	</div>
	<div style="margin-bottom: 7px;" align="left">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesisScoore()" style="margin-left: 190px; width: 130px;">保存评分标准</a>
	</div>
</form>
</div>