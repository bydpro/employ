<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<%@ page language="java" import="srmt.java.common.Constants"%>
<style type="text/css">
    .uploadify-button {
        background-color: transparent;
        border: none;
        padding: 0;
        width: 320px;
    }
    .uploadify:hover .uploadify-button {
        background-color: transparent;
    }
</style>
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
		$('#resTecFf').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('research/queryResearchList.do?' + Math.random(), $('#resTecFf').serializeObject(), function(data) {
			$('#resTecDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#resTecDg');
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
<form id="resTecFf" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="researchName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
		<label for="researchName">教师姓名:</label> <input class="easyui-textbox"
			type="text" name="userName" style="width: 200px; height: 30px;" />
		<label for="researchName">科研名称:</label> <input class="easyui-textbox"
			type="text" name="researchName" style="width: 200px; height: 30px;" />
		<label for="organName">科研类型:</label> <input id="cc"
			class="easyui-combobox" name="dictValue"
			style="width: 200px; height: 30px;"
			data-options="valueField:'dictvalue',textField:'dictname',url:'research/queryResearchType.do',editable:false">
		</div>
		<div style="margin-bottom: 7px;">
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 833px"
			onclick="doSearch()"> <input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm()" />
	</div>

</form>
<table id="resTecDg" title="教师科研信息列表" 
	style="width: 1050px; height: 81%;" toolbar="#toolbar4resTec" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:20">
	<thead>
		<tr>
			<th field="USERNUM" width="50">用户编号</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="RESEARCHNAME" width="50">科研名称</th>
			<th field="DICTNAME" width="50">科研类型</th>
			<th field="MOBILE" width="50">移动电话</th>
			<th field="EMAIL" width="50">电子邮箱</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>