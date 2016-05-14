<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">

	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getPatentScore.do",
			async : true,
			success : function(data) {
				$('#patentScoreForm').form('load', data);

			},
		});
	});
	
	function saveThesisScoore() {
		$('#patentScoreForm').form('submit', {
			url : "research/savetPatentScore.do",
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
<form id="patentScoreForm" method="post">
	<div style="margin-bottom: 7px;margin-top: 80px;">
		<label style="width: 160px;">发明专利（职务发明）</label>
		<label>首位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="inventFist"> 
		<label>成功转让:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="inventIsMove">
	</div>
	<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">实用新型专利（职务发明）</label>
		<label>首位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="practicalFirst"> 
		<label>成功转让:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="practicalIsMove">		
	</div>
		<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">外观设计专利（职务发明）</label>
		<label>首位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="viewFirst"> 
		<label>成功转让:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="viewIsMove">		
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 160px;">软件著作权</label>
		<label>首位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="softFirst"> 
		<label>成功转让:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="softIsMove">		
	</div>
	<div style="margin-bottom: 7px;" align="left">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesisScoore()" style="margin-left: 190px; width: 130px;">保存评分标准</a>
	</div>
</form>
</div>