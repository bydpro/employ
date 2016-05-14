<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">

	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getThesisScore.do",
			async : true,
			success : function(data) {
				$('#thesisScoreForm').form('load', data);

			},
		});
	});
	
	function saveThesisScoore() {
		$('#thesisScoreForm').form('submit', {
			url : "research/saveThesisScore.do",
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
<form id="thesisScoreForm" method="post">
	<div style="margin-bottom: 7px;margin-top: 80px;">
		<label style="width: 130px;">SCI收录论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisShousci"> 
		<label>教务处指定的十四种教学期刊:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisJiaowu">		
	</div>
	<div style="margin-bottom: 7px;" >		
		<label style="width: 130px;">EI收录论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisEishoulu">
		<label style="width:163px;">ISTP收录论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisShoulu">
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 130px;">中文核心期刊论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisChinese">
		<label style="width:163px;">国际会议论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisGuoji">		
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 130px;">EI源刊:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisEiyuan">
		<label style="width:163px;">EI收录国际期刊论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisEishou">
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 130px;">其他教学研究论文:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="thesisOther">
	</div>
	<div style="margin-bottom: 7px;" align="left">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesisScoore()" style="margin-left: 190px; width: 130px;">保存评分标准</a>
	</div>
</form>
</div>