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
		reFreshGrid(1,10);
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
		debugger
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('Confirm','确认删除所选用户么?',function(r){
				if (r){
					$.post('userMng/delUser.do',{userId:row.USERID},function(result){
						if (result.success){
							$.messager.alert('提示','删除成功!');
							$('#dg').datagrid('reload');	// reload the user data
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
					$('#dg').datagrid('reload'); // reload the user data
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
		debugger
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
							$('#dg').datagrid('reload');	// reload the user data
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
		debugger
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
							$('#dg').datagrid('reload');	// reload the user data
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


	$(function() {
		$("#dg").datagrid({
			url :'userMng/queryUserList.do',
			pageSize : 10,//每页显示的记录条数，默认为10  
			pageList : [ 10, 20, 35, 50, 100 ],//可以设置每页记录条数的列表  
			pagination : true,
			fitColumns : true,
			singleSelect : true,
			striped : true,
			rownumbers : true,
		});
		reFreshGrid(1,10);
		var pager = $('#dg').datagrid('getPager');
		$(pager).pagination({
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onSelectPage : function(pageNumber, pageSize) {
				debugger
				reFreshGrid(pageNumber,pageSize);//每次更换页面时触发更改   
			}
		});
	})

	function reFreshGrid(pageNumber,pageSize) {
		debugger
		var dg = $('#dg');
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		
		$('#pageNum').val(pageNumber);
		$('#pageSize').val(pageSize);
		//异步获取数据到javascript对象，入参为查询条件和页码信息  
		$.post('userMng/queryUserList.do?' + Math.random(), $('#ff').serializeObject(), 
			function(data) {
			debugger
			//注意此处从数据库传来的data数据有记录总行数的total列  
			var total = data[0].total;
			data.shift();
			$('#dg').datagrid('loadData', data);
			pager.pagination({
				//更新pagination的导航列表各参数  
				total : total,//总数  
				pageSize : pageSize,//行数  
				pageNumber : pageNumber//页数  
			});
		});
	}
</script>
<form id="ff" method="post">
    <div style="margin-bottom: 7px">
		<label for="username">姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
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
		<label for="loginId">用户名:</label>
		<input class="easyui-textbox" type="text" name="loginId"  style="width:200px;height:30px;"/>
		<label>是否有效:&nbsp;&nbsp;</label>
        <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
        </span>
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:440px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
   	   
   	    <input  type="text" name="pageNum" hidden="true" id="pageNum"/>
   	    <input  type="text" name="pageSize" hidden="true" id="pageSize"/>
    </div>
    
</form>
<table id="dg" title="用户列表" 
	style="width: 1050px; height: 78%;" toolbar="#toolbar">
	<thead>
		<tr>
			<th field="LOGINID" width="50">用户名</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="SEX" width="50" formatter="formatSex">性别</th>
			<th field="EMAIL" width="50">电子邮箱</th>
			<th field="MOBILE" width="50">移动电话</th>
			<th field="ORGANNAME" width="50">所属单位</th>
			<th field="ISVALID" width="50" formatter="formatValue">是否有效</th>
			<th field="BIRTHDAY" width="50">生日</th>
		</tr>
	</thead>
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delUser()">移除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="layoutUser()">注销</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="unLayoutUser()">取消注销</a>
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
			<input id="cc" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
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