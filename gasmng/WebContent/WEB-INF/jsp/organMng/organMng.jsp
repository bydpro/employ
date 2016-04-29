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
		$('#organFf').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('organMng/queryOragnList.do?' + Math.random(), $('#organFf').serializeObject(), function(data) {
			$('#organDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#organDg');
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
	
	function editOrgan() {
		var row = $('#organDg').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "organMng/getOrganInfo.do",
				data : {
					organId : row.ORGANID
				},
				async : true,
				success : function(data) {
					$('#organDlg').dialog('open').dialog('setTitle', '修改单位');
					$('#submitForm').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function delOrgan() {
		var row = $('#organDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选单位么?', function(r) {
				if (r) {
					$.post('organMng/delOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#organDg').datagrid('reload',getData());// reload the user data
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

	function saveOrgan() {
		$('#submitForm').form('submit', {
			url : "organMng/saveOrgan.do",
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
					$.messager.alert('提示', '保存成功!');
					$('#organDlg').dialog('close'); // close the dialog
					$('#organDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}

	function formatValue(val, row) {
		if (val == 1) {
			return '是';
		} else {
			return '否';
		}
	}

	function layoutOrgan() {
		var row = $('#organDg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 0) {
				$.messager.alert('提示', '当前单位已经处于无效状态，无需再次注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中单位设置为无效么?', function(r) {
				if (r) {
					$.post('organMng/layoutOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '注销成功!');
							$('#organDg').datagrid('reload',getData()); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : '注销失败'
							});
						}
					}, 'json');
				}
			});
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function unLayoutOrgan() {
		var row = $('#organDg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 1) {
				$.messager.alert('提示', '当前单位已经处于有效状态，无需再次取消注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中单位设置为有效么?', function(r) {
				if (r) {
					$.post('organMng/unLayoutOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '取消注销成功!');
							$('#organDg').datagrid('reload',getData()); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : '取消注销'
							});
						}
					}, 'json');
				}
			});
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function addOrgan() {
		$('#organDlg').dialog('open').dialog('setTitle', '新增单位');
		$('#submitForm').form('clear');
	}
</script>
<form id="organFf" method="post">
     <div style="margin-bottom: 7px;">
		<label for="organName">单位名称:</label>
		<input class="easyui-textbox" type="text" name="organName"  style="width:200px;height:30px;"/>
		<label for="organCode">单位代码:</label>
		<input class="easyui-textbox" type="text" name="organCode"  style="width:200px;height:30px;"/>
		<label>是否有效:&nbsp;&nbsp;</label>
        <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
        </span>
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:200px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
    </div>
    
</form>
<table id="organDg" title="单位列表" 
	style="width: 1050px; height: 85%;" toolbar="#toolbar4Organ" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="ORGANNAME" width="50">单位名称</th>
			<th field="ORGANID" width="50" hidden="true">ORGANID</th>
			<th field="ORGANCODE" width="50">单位代码</th>
			<th field="ORGANADDRESS" width="50">单位地址</th>
			<th field="ISVALID" width="50" formatter="formatValue">是否有效</th>
		</tr>
	</thead>
</table>
<div id="toolbar4Organ">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOrgan()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOrgan()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delOrgan()">移除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="layoutOrgan()">注销</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unLayoutOrgan()">取消注销</a>
</div>

<div id="organDlg" class="easyui-dialog" style="width:590px;height:300px;padding:10px 20px"
		closed="true" buttons="#organDlg-buttons">
	<div class="ftitle">单位信息</div>
	<form id="submitForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="organId" hidden="true"/>
			<label>单位名称：</label>
			<input name="organName" class="easyui-validatebox" required="true" style="width:200px;height:30px;">
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>单位代码：</label>
			<input name="organCode" class="easyui-validatebox" data-options="required:true" style="width:200px;height:30px;">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>单位地址：</label>
			<input name="address" style="width:200px;height:30px;">
		</div>
		<div  style="margin-bottom: 7px;">
		    <label>是否有效:</label>
		    <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
            </span>
		</div>
	</form>
</div>
<div id="organDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOrgan()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#organDlg').dialog('close')">取消</a>
</div>