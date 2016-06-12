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

	function clearForm4ff4dict() {
		$('#ff4dict').form('clear');
	}

	function doSearch4ff4dict() {
		getData4dict();
	}

	function addDict() {
		$('#dlg4dict').dialog('open').dialog('setTitle', '新增字典值');
		$('#fm4dict').form('clear');  
	}

	function editDict() {
		var row = $('#dg4dict').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "userMng/getDictInfo.do",
				data : {
					dictId : row.dict_id
				},
				async : true,
				success : function(data) {
					$('#dlg4dict').dialog('open').dialog('setTitle', '修改字典值');
					$('#fm4dict').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function deDict() {
		var row = $('#dg4dict').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选字典值么?', function(r) {
				if (r) {
					$.post('userMng/delDict.do', {
						dictId : row.dict_id
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#dg4dict').datagrid('reload',getData4dict()); // reload the user data
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

	function saveDict() {
		$('#fm4dict').form('submit', {
			url : "userMng/saveDict.do",
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
					$('#dlg4dict').dialog('close'); // close the dialog
					$('#dg4dict').datagrid('reload',getData4dict()); // reload the user data
				}
			}
		});
	}

	function getData4dict() {
		$.post('userMng/querySysDict.do?' + Math.random(), $('#ff4dict')
				.serializeObject(), function(data) {
			$('#dg4dict').datagrid({
				loadFilter : pagerFilter4dict
			}).datagrid('loadData', data);
		});
	}
	
	function pagerFilter4dict(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg4dict = $('#dg4dict');
		var opts = dg4dict.datagrid('options');
		var pager = dg4dict.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg4dict.datagrid('loadData', data);
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
		$('#ff4dict').form('clear'); 
		getData4dict();
	})
	
	
		function formatDict(val, row) {
		if (val == 'reward_type') {
			return '奖励类型';
		} else if (val == 'patent_type'){
			return '专利类型';
		}else if (val == 'project_type'){
			return '项目类型';
		}else if (val == 'thesis_type'){
			return '论文类型';
		}else if (val == 'patent_people'){
			return '专利人权类别';
		}
	}
</script>
<form id="ff4dict" method="post" style="margin-top: 20px;">
     <div style="margin-bottom: 7px;">
     <label>字典值类型:</label> 
			<select
				class="easyui-combobox" name="dictType" data-options="editable:false"
				 style="width: 200px; height: 30px;">
				<option value="reward_type">奖励类型</option>
				<option value="patent_type">专利类型</option>
				<option value="project_type">项目类型</option>
				<option value="thesis_type">论文类型</option>
				<option value="patent_people">专利人权类别</option>
			</select>
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:220px " onclick="doSearch4ff4dict()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm4ff4dict()"/>
    </div>
    
</form>
<table id="dg4dict" title="字典值列表" 
	style="width: 700px; height: 73%;" toolbar="#toolbar4dict" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERID"  hidden="true">USERID</th>
			<th field="dict_name" width="40px;" align="center">字典值名称</th>

			<th field="dict_type" width="40px;" align="center" formatter="formatDict">字典值类型</th>
		</tr>
	</thead>
</table>
<div id="toolbar4dict">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDict()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editDict()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deDict()">移除</a>
</div>

<div id="dlg4dict" class="easyui-dialog"
	style="width: 390px; height: 200px; padding: 10px 20px" closed="true"
	buttons="#dlg4dict-buttons" modal="true" align="center">
	<form id="fm4dict" method="post">
		<div style="margin-bottom: 7px;">
			<input name="dictId" hidden="true" /> <label>字典值名称</label>
			<input name="dictName" class="easyui-textbox" required="true"
				style="width: 200px; height: 30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>字典值类型</label> 
			<select
				class="easyui-combobox" name="dictType" data-options="editable:false"
				required="true" style="width: 200px; height: 30px;">
				<option value="reward_type">奖励类型</option>
				<option value="patent_type">专利类型</option>
				<option value="project_type">项目类型</option>
				<option value="thesis_type">论文类型</option>
				<option value="patent_people">专利人权类别</option>
			</select>
		</div>
	</form>
</div>
<div id="dlg4dict-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDict()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg4dict').dialog('close')">取消</a>
</div>