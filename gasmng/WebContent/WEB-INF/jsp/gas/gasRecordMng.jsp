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
		$('#gasFf').form('clear');
	}

	function doSearch() {
		getData();
	}
	function getData() {
		$.post('oilStorage/queryGasRecord.do?' + Math.random(), $('#gasFf')
				.serializeObject(), function(data) {
			$('#gasDg').datagrid({
				loadFilter : pagerFilter
			}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#gasDg');
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

	$(function() {
		getData();

	})

	function delGasRecord() {
		var row = $('#gasDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选记录么?', function(r) {
				if (r) {
					$.post('oilStorage/delGasRecord.do', {
						gasId : row.gasid
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#gasDg').datagrid('reload', getData());// reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : '删除失败'
							});
						}
					}, 'json');
				}
			});
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	
	function addGasRecord() {
		$('#gasDlg').dialog('open').dialog('setTitle', '新增记录');
		$('#gasForm').form('clear');
	}
	
	function saveGasRecord() {
		$('#gasForm').form('submit', {
			url : "oilStorage/saveGasRecord.do",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				result=JSON.parse(result);
				if (result.error) {
					$.messager.alert({
						title : 'Error',
						msg : result.error
					});
				} else {
					$.messager.alert('提示', '保存成功!');
					$('#gasDlg').dialog('close'); // close the dialog
					$('#gasDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function editGasRecord() {
		var row = $('#gasDg').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "oilStorage/getGasRecord.do",
				data : {
					gasId : row.gasid
				},
				async : true,
				success : function(data) {
					$('#gasDlg').dialog('open').dialog('setTitle', '修改记录');
					$('#gasForm').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
</script>
<form id="gasFf" method="post">
	<div style="margin-bottom: 7px">
		<label for="username">姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label> <input
			class="easyui-textbox" type="text" name="userName"
			style="width: 200px; height: 30px;" /> <label for="email">电子邮箱:</label>
		<input class="easyui-textbox" type="text" name="email"
			style="width: 200px; height: 30px;" /> <label for="mobile">移动电话:</label>
		<input class="easyui-textbox" type="text" name="mobile"
			style="width: 200px; height: 30px;" />
		<label for="mobile">卡&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
		<input class="easyui-textbox" type="text" name="userNum"
			style="width: 200px; height: 30px;" />
	</div>
	<div style="margin-bottom: 7px">
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 810px"
			onclick="doSearch()">
		<input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm()" />
	</div>
</form>
<table id="gasDg" title="加油记录列表" style="width: 1050px; height: 78%;"
	toolbar="#toolbar4Gas"
	data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="username" width="60" align="center">加油人</th>
			<th field="mobile" width="60" align="center">加油人移动电话</th>
			<th field="email" width="60" align="center">加油人邮箱</th>
			<th field="gasusernum" width="60" align="center">卡号</th>
			<th field="gasid" width="50" hidden="true"></th>
			<th field="gastype" width="50" align="center">油品类型</th>
			<th field="gasprice" width="50" >油价</th>
			<th field="gasvolume" width="50">加油量</th>
			<th field="gastime" width="60" align="center">加油时间</th>						
		</tr>
	</thead>
</table>
<div id="toolbar4Gas">
 <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addGasRecord()">新增记录</a> 
 <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGasRecord()">修改记录</a>
 <a	href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"	onclick="delGasRecord()">移除记录</a>
</div>
<div id="gasDlg" class="easyui-dialog" style="width:490px;height:280px;padding:10px 20px"
		closed="true" buttons="#gasDlg-buttons" align="center">
	<form id="gasForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="gasId" hidden="true"/>
			<label>卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号&nbsp:</label>
			<input name="gasUserNum" id ="gasUserNum" class="easyui-numberbox" required="true" style="width:200px;height:30px;" data-options="precision:0,min :201610000001,max:999999999999"
			   >
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label for="gasType">油&nbsp;品&nbsp;类&nbsp;型:</label>
			<input id="cc3" class="easyui-combobox" name="gasType" style="width:200px;height:30px;"
    			data-options="valueField:'dictvalue',textField:'dictname',url:'oilStorage/queryOilType.do'" required="true">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>油&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价&nbsp;:</label>
			<input name="gasPrice" style="width:200px;height:30px;" required="true" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'$'">
		</div>
		<div  style="margin-bottom: 7px;">
		 	<label>加&nbsp;&nbsp;&nbsp;油&nbsp;&nbsp;&nbsp;量&nbsp;:</label>
			<input name="gasVolume" style="width:200px;height:30px;" required="true" class="easyui-numberbox" data-options="precision:0">
		</div>
	</form>
</div>
<div id="gasDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveGasRecord()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#gasDlg').dialog('close')">取消</a>
</div>