<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}

	function clearForm() {
		$('#queryRewardTongForm').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();

	})

	function getData() {
		$.post('research/queryRewardTongList.do?' + Math.random(), $(
				'#queryRewardTongForm').serializeObject(), function(data) {
			$('#rewardTongDg').datagrid({
				loadFilter : pagerFilter
			}).datagrid('loadData', data);
			
			$("#size4reward").html(data.length);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#rewardTongDg');
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg.datagrid('loadData', data);
			}
		});
		if (!data.originalRows) {
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		return data;
	}
</script>
<form id="queryRewardTongForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="projectName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
		<label for="projectName">教师姓名:</label> <input class="easyui-textbox"
			type="text" name="userName" style="width: 200px; height: 30px;" />
		<label for="rewardName">奖励名称:</label> <input class="easyui-textbox"
			type="text" name="rewardName" style="width: 200px; height: 30px;" />
		<label>获奖类别：</label>
		<input id="rewardType" class="easyui-combobox" name="rewardType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryRewardType.do'">
	</div>
	<div style="margin-bottom: 7px;">
		<label for="rewardOrgan">奖励单位:</label> 
		<input class="easyui-textbox"	type="text" name="rewardOrgan" style="width: 200px; height: 30px;" />
		<label>	获奖时间:</label>
		<input name="rewardTime" type="text" style="width:200px;height:30px;" class="easyui-datebox"/>
		<input class="easyui-linkbutton" type="button" value="检索"
			style="width: 198px; height: 30px; margin-left: 325px"
			onclick="doSearch()"> 
	</div>

</form>
<div style="color: blue;"><font size="5">共检索到</font> <font id="size4reward" size="5"></font><font size="5">条记录</font></div>
<table id="rewardTongDg" title="奖励信息统计列表" 
	style="width: 1050px; height: 70%;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNUM" width="50">教师编号</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="REWARDNAME" width="50">奖励名称</th>
			<th field="REWARDTYPE" width="50">奖励类别</th>
			<th field="REWARDORGAN" width="50">奖励单位</th>
			<th field="REWARDUSER" width="50">获奖所有人员</th>
			<th field="REWARDTIME" width="50">获奖时间</th>
			<th field="workload" width="50">获奖科研分</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>