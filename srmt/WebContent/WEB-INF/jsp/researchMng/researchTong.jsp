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
		$('#queryScoreTongFf').form('clear');
	}

	function doSearch() {
		debugger
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('research/queryScore4Tong.do?' + Math.random(), $('#queryScoreTongFf').serializeObject(), function(data) {
			$('#resTongDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#resTongDg');
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
	
	

	function lookTecherInfo() {
		var row = $('#resTongDg').datagrid('getSelected');
		if (row) {
			Open("教师科研详细信息","research/enterWorkload4Tec.do?userNum="+row.USERNUM);

		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	

	//在右边center区域打开菜单，新增tab
	function Open(text, url) {
		$('#tabs').tabs('add', {
			title : text,
			closable : true,
			href : url
		});

	}
</script>
<form id="queryScoreTongFf" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;" >
		<label for="userNum">教师编号:</label> 
		<input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 573px"
			onclick="doSearch()">
		<input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm()" />
	</div>

</form>
<table id="resTongDg" title="教师科研统计列表" 
	style="width: 1050px; height: 85%;" toolbar="#toolbar4resTongDg" data-options="
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
			<th field="thesis" width="50">论文数量和科研分</th>
			<th field="patent" width="50">专利数量和科研分</th>
			<th field="project" width="50">项目数量和科研分</th>
			<th field="reward" width="50" >奖励数量和科研分</th>
			<th field="sum" width="50" >科研总分</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="toolbar4resTongDg">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="lookTecherInfo()">查看教师科研详细信息</a> 
</div>