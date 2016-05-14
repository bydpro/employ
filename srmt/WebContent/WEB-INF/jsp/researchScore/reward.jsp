<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">

	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getRewardScore.do",
			async : true,
			success : function(data) {
				$('#rewardScoreForm').form('load', data);

			},
		});
	});
	
	function saveThesisScoore() {
		$('#rewardScoreForm').form('submit', {
			url : "research/savetRewardScore.do",
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
<div style="margin-left: 100px;">
<form id="rewardScoreForm" method="post">
	<div style="margin-bottom: 7px;margin-top: 80px;">
		<label style="width: 160px;">省部级一等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenFirst"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenSecond">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenThird">
		<label>第四位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenFourth">
		<label>第五位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shenFifth">
	</div>
	<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">省部级二等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen2First"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen2Second">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen2Third">
		<label>第四位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen2Fourth">
		<label>第五位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen2Fifth">	
	</div>
		<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">省部级三等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen3First"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen3Second">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen3Third">
		<label>第四位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen3Fourth">
		<label>第五位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="shen3Fifth">	
	</div>
	<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">地市级一等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishiFirst"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishiSecond">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishiThird">	
	</div>
		<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">地市级二等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi2First"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi2Second">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi2Third">	
	</div>
		<div style="margin-bottom: 7px;" >		
		<label style="width: 160px;">地市级三等奖</label>
		<label>第一位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi3First"> 
		<label>第二位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi3Second">
		<label>第三位:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="dishi3Third">	
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 160px;">校级</label>
		<label>一等奖:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="schoolFirst"> 
		<label>二等奖:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="schoolSecond">
		<label>三等奖:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="schoolThird">		
	</div>
	<div style="margin-bottom: 7px;">
		<label style="width: 160px;">其他奖项</label>
		<label>优秀教学奖:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name="youxiuJiaoxue"> 
		<label>青年教学能手:</label>
		<input class="easyui-numberbox"  data-options="precision:1,groupSeparator:','" 
			style="width: 100px; height: 30px;" required="true" name=yongTeach>		
	</div>
	<div style="margin-bottom: 7px;" align="left">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesisScoore()" style="margin-left: 190px; width: 130px;">保存评分标准</a>
	</div>
</form>
</div>