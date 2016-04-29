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
	$(function() {
		getData();

	})

	function getData() {
		$.post('oilStorage/queryOilStorage.do?' + Math.random(), $('#queryOilForm')
				.serializeObject(), function(data) {
			$('#oildg').datagrid({
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
		var dg = $('#oildg');
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
	
	function editOil() {
		var row = $('#oildg').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "oilStorage/getOliStirage.do",
				data : {
					oilStorageId : row.oil_storage_id
				},
				async : true,
				success : function(data) {
					$('#oilDlg').dialog('open').dialog('setTitle', '修改');
					$('#oilForm').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function delOliStirage() {
		var row = $('#oildg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选入库记录么?', function(r) {
				if (r) {
					$.post('oilStorage/delOliStirage.do', {
						oilStorageId : row.oil_storage_id
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#oildg').datagrid('reload',getData());// reload the user data
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

	function saveOil() {
		$('#oilForm').form('submit', {
			url : "oilStorage/saveOliStirage.do",
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
					$('#oilDlg').dialog('close'); // close the dialog
					$('#oildg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function addOil() {
		$('#oilDlg').dialog('open').dialog('setTitle', '入库');
		$('#oilForm').form('clear');
	}
	
	function clearForm() {
		$('#queryOilForm').form('clear');
	}

	function doSearch() {
		getData();
	}
</script>
<form id="queryOilForm" method="post">
     <div style="margin-bottom: 7px;">
		<label for="oilType">油品类型:</label>
		<input id="cc3" class="easyui-combobox" name="oilType" style="width:200px;height:30px;"
    			data-options="valueField:'dictvalue',textField:'dictname',url:'oilStorage/queryOilType.do'">
		<label for="oilTankId">油罐编号:</label>
		<input class="easyui-textbox" type="text" name="oilTankId"  style="width:200px;height:30px;"/>
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:200px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
    </div>
    
</form>
<table id="oildg" title="入库列表" style="width: 1050px; height: 78%;"
	toolbar="#toolbar4oildg" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="oil_storage_id" width="50" hidden="true">用户名</th>
			<th field="oil_type" width="50" >油品类型</th>
			<th field="olil_num" width="50"  align="center">数量</th>
			<th field="oil_tank_id" width="50" align="center">油罐编号</th>
			<th field="oil_receive_time" width="50" >入库时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar4oildg">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOil()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOil()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delOliStirage()">移除</a>
</div>

<div id="oilDlg" class="easyui-dialog" style="width:590px;height:300px;padding:10px 20px"
		closed="true" buttons="#oilDlg-buttons">
	<form id="oilForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="oilStorageId" hidden="true"/>
			<label>油品类型：</label>
			<input id="cc" class="easyui-combobox" name="oilType" style="width:200px;height:30px;"
    			data-options="valueField:'dictvalue',textField:'dictname',url:'oilStorage/queryOilType.do'" required="true">
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>油品数量：</label>
			<input name="olilNum" class="easyui-numberbox" data-options="required:true" style="width:200px;height:30px;"
			 precision="0">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>油罐编号：</label>
			<input name="oilTankId" style="width:200px;height:30px;" class="easyui-validatebox" required="true" >
		</div>
		<div style="margin-bottom: 7px;">
			<label>入库时间：</label>
			<input name="oilReceiveTime" style="width:200px;height:30px;" class="easyui-datetimebox" required="true">
		</div>
	</form>
</div>
<div id="oilDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOil()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#oilDlg').dialog('close')">取消</a>
</div>