<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<style>
#text-box {
	width:200px;
    height: 30px;
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
		$('#ff').form('clear');
	}

	function doSearch() {
		getData();
	}
	
	function addUser(){
		$('#dlg').dialog('open').dialog('setTitle','新增用户');
		$('#fm').form('clear');
	}
	
	
	function editUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.ajax({
				type: "POST",
	            dataType: "json",
	            url: "userMng/getUserInfo.do",
	            data: {userId:row.USERID},
	            async:true,
	            success: function (data) {
	        		$('#dlg').dialog('open').dialog('setTitle','修改用户');
	    			$('#fm').form('load',data);
	            },
			})
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	
	function delUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('Confirm','确认删除所选用户么?',function(r){
				if (r){
					$.post('userMng/delUser.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','删除成功!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '删除失败'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	
	function saveUser() {
		$('#fm').form('submit', {
			url : "userMng/saveUser.do",
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
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	function formatValue(val,row){
		if (val == 1){
			return '是';
		} else {
			return '否';
		}
	}
	
	function formatType(val,row){
		if (val == 3){
			return '系统管理员';
		} else if (val == 2){
			return '普通管理员';
		}else{
			return '普通用户';
		}
	}

	function formatSex(val, row) {
		if (val == 1) {
			return '男';
		} else if (val == 0) {
			return '女';
		} else {
			return '未知';
		}
	}
	
	function layoutUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			if(row.ISVALID==0){
				$.messager.alert('提示','当前用户已经处于无效状态，无需再次注销!');
				return;
			}
			$.messager.confirm('Confirm','确认将选中用户设置为无效么?',function(r){
				if (r){
					$.post('userMng/layoutUser.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','注销成功!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '注销失败'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	
	function unLayoutUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			if(row.ISVALID==1){
				$.messager.alert('提示','当前用户已经处于有效状态，无需再次取消注销!');
				return;
			}
			$.messager.confirm('Confirm','确认将选中用户设置为有效么?',function(r){
				if (r){
					$.post('userMng/unLayoutUser.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','取消注销成功!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '取消注销'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	function getData() {
		$.post('userMng/queryUserList.do?' + Math.random(), $('#ff').serializeObject(), function(data) {
			$('#dg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#dg');
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
	
	function isXTAdmin(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			if(row.USERTYPE==3){
				$.messager.alert('提示','当前用户已经是系统管理员，无需再次设置!');
				return;
			}
			$.messager.confirm('Confirm','确认将选中用户设置为系统管理员么?',function(r){
				if (r){
					$.post('userMng/isXTAdmin.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','已成功将用户设置为系统管理员!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '设置失败'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	
	function isAdmin(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			if(row.USERTYPE==2){
				$.messager.alert('提示','当前用户已经是普通管理员，无需再次设置!');
				return;
			}
			$.messager.confirm('Confirm','确认将选中用户设置为普通管理员么?',function(r){
				if (r){
					$.post('userMng/isAdmin.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','已成功将用户设置为普通管理员!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '设置失败'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
	
	function isNormalUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			if(row.USERTYPE==1){
				$.messager.alert('提示','当前用户已经是普通用户，无需再次设置!');
				return;
			}
			$.messager.confirm('Confirm','确认将选中用户设置为普通用户么?',function(r){
				if (r){
					$.post('userMng/isNormalUser.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','已成功将用户设置为普通用户!');
							$('#dg').datagrid('reload',getData());	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: '设置失败'
							});
						}
					},'json');
				}
			});
		}else{
			$.messager.alert('提示','请选中一行!');
		}
	}
</script>
<form id="ff" method="post">
    <div style="margin-bottom: 7px">
		<label for="username">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
		<input class="easyui-textbox" type="text" name="userName"  style="width:200px;height:30px;"/>
		<label for="email">电子邮箱:</label>
		<input class="easyui-textbox" type="text" name="email" style="width:200px;height:30px;"/>
		<label for="mobile">移动电话:</label>
		<input class="easyui-textbox" type="text" name="mobile" style="width:200px;height:30px;"/>
		<label for="organId">所属单位:</label>
		<input id="cc" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOragn.do'">
    </div>
     <div style="margin-bottom: 7px;">
		<label for="loginId">用户卡号:</label>
		<input class="easyui-textbox" type="text" name="userNum"  style="width:200px;height:30px;"/>
		<label>是否有效:&nbsp;&nbsp;</label>
        <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
        </span>
         <label style="margin-left: 125px">是否为管理员:</label>
		 <span class="radioSpan">
         <input type="radio" name="isAdmin" value="1">是</input>&nbsp;&nbsp;&nbsp;
         <input type="radio" name="isAdmin" value="0">否</input>
         </span>
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:175px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
   	   
   	    <input  type="text" name="pageNum" hidden="true" id="pageNum"/>
   	    <input  type="text" name="pageSize" hidden="true" id="pageSize"/>
    </div>
    
</form>
<table id="dg" title="用户列表" 
	style="width: 1050px; height: 78%;" toolbar="#toolbar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="USERNUM" width="60" align="center">卡号</th>
			<th field="SEX" width="50" formatter="formatSex" align="center">性别</th>
			<th field="BIRTHDAY" width="50" align="center">生日</th>
			<th field="USERTYPE" width="50" formatter="formatType">用户类型</th>
			<th field="EMAIL" width="50">电子邮箱</th>
			<th field="MOBILE" width="50" align="center">移动电话</th>
			<th field="ORGANNAME" width="50">所属单位</th>
			<th field="ISVALID" width="50" formatter="formatValue" align="center">是否有效</th>
			<th field="ISADMIN" width="50" formatter="formatValue" align="center">是否管理员</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delUser()">移除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="layoutUser()">注销</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unLayoutUser()">取消注销</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="isXTAdmin()">设置为系统管理员</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="isAdmin()">设置为普通管理员</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="isNormalUser()">设置为普通用户</a>
</div>

<div id="dlg" class="easyui-dialog" style="width:590px;height:300px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
	<div class="ftitle">用户信息</div>
	<form id="fm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="userId" hidden="true"/>
			<label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
			<input name="userName" class="easyui-validatebox" required="true" style="width:200px;height:30px;">
			<label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
            <span class="radioSpan">
                <input type="radio" name="sex" value="1">男</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="0">女</input>
            </span>
 		</div>
		<div style="margin-bottom: 7px;">
			<label>电子邮箱</label>
			<input name="email" class="easyui-validatebox" data-options="required:true,validType:'email'" style="width:200px;height:30px;">
			<label>移动电话</label>
			<input name="mobile" style="width:200px;height:30px;" data-options="required:true,validType:'mobile'">
		</div>
		<div  style="margin-bottom: 7px;">
			<label>所属单位</label>
			<input id="cc2" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOragn.do'">
			<label>生日日期</label>
			<input type="text" class="easyui-datebox" style="width:200px;height:30px;" name="birhtday">
		</div>
		<div  style="margin-bottom: 7px;">
			<label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址</label>
			<input name="address" class="easyui-validatebox"  style="width:455px;height:30px;">
		</div>
		<div  style="margin-bottom: 7px;">
		    <label>是否为管理员:</label>
		    <span class="radioSpan">
                <input type="radio" name="isAdmin" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isAdmin" value="0">否</input>
            </span>
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>