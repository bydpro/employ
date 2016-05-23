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
		$('#deptFf').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('organMng/queryDeptList.do?' + Math.random(), $('#deptFf').serializeObject(), function(data) {
			$('#deptDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#deptDg');
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
	
	function editdept() {
		var row = $('#deptDg').datagrid('getSelected');
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
					$('#deptDlg').dialog('open').dialog('setTitle', '修改院系');
					$('#submitForm').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function deldept() {
		var row = $('#deptDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选院系么?', function(r) {
				if (r) {
					$.post('organMng/delOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#deptDg').datagrid('reload',getData());// reload the user data
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

	function savedept() {
		$('#submitForm').form('submit', {
			url : "organMng/saveDept.do",
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
					$('#deptDlg').dialog('close'); // close the dialog
					$('#deptDg').datagrid('reload',getData()); // reload the user data
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

	function layoutdept() {
		var row = $('#deptDg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 0) {
				$.messager.alert('提示', '当前院系已经处于无效状态，无需再次注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中院系设置为无效么?', function(r) {
				if (r) {
					$.post('organMng/layoutOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '注销成功!');
							$('#deptDg').datagrid('reload',getData()); // reload the user data
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

	function unLayoutdept() {
		var row = $('#deptDg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 1) {
				$.messager.alert('提示', '当前院系已经处于有效状态，无需再次取消注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中院系设置为有效么?', function(r) {
				if (r) {
					$.post('organMng/unLayoutOrgan.do', {
						organId : row.ORGANID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '取消注销成功!');
							$('#deptDg').datagrid('reload',getData()); // reload the user data
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

	function adddept() {
		$('#deptDlg').dialog('open').dialog('setTitle', '新增院系');
		$('#submitForm').form('clear');
	}
</script>
<form id="deptFf" method="post" style="margin-top: 20px;">
     <div style="margin-bottom: 7px;" >
		<label for="organName" style="margin-left: 20px;">院系名称:</label>
		<input class="easyui-textbox" type="text" name="organName"  style="width:200px;height:30px;"/>
		<label for="organCode" style="margin-left: 20px;">院系代码:</label>
		<input class="easyui-textbox" type="text" name="organCode"  style="width:200px;height:30px;"/>
		<label for="organId" style="margin-left: 20px;">所属学院:</label>
		<input id="cc" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false">
		<label style="margin-left: 20px;">是否有效:&nbsp;&nbsp;</label>
        <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
        </span>
    </div>
     <div style="margin-bottom: 7px;">
     	<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:840px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
     </div>
    
</form>
<table id="deptDg" title="院系列表" 
	style="width: 1050px; height: 77%;" toolbar="#toolbar4dept" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:20">
	<thead>
		<tr>
			<th field="ORGANNAME" width="50">院系名称</th>
			<th field="PARENT" width="50">所属学院</th>
			<th field="ORGANID" width="50" hidden="true">deptID</th>
			<th field="ORGANCODE" width="50">院系代码</th>
			<th field="ISVALID" width="50" formatter="formatValue">是否有效</th>
		</tr>
	</thead>
</table>
<div id="toolbar4dept">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="adddept()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editdept()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deldept()">移除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="layoutdept()">注销</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unLayoutdept()">取消注销</a>
</div>

<div id="deptDlg" class="easyui-dialog" style="width:390px;height:250px;padding:10px 20px"
		closed="true" buttons="#deptDlg-buttons" align="center" modal="true">
	<form id="submitForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="organId" hidden="true"/>
			<label>院系名称：</label>
			<input name="organName" class="easyui-validatebox" required="true" style="width:200px;height:30px;">
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>院系代码：</label>
			<input name="organCode" class="easyui-validatebox" data-options="required:true" style="width:200px;height:30px;">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>所属学院：</label> <input
				class="easyui-combobox" name="parent"
				style="width: 200px; height: 30px;"
				data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false ">
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
<div id="deptDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savedept()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#deptDlg').dialog('close')">取消</a>
</div>