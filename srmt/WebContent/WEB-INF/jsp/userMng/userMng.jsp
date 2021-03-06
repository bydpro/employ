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

	function clearForm4ff() {
		$('#ff').form('clear');
	}

	function doSearch4ff() {
		getData4user();
	}

	function addUser() {
		$('#dlg').dialog('open').dialog('setTitle', '新增用户');
		$('#fm').form('clear');
		$('[name="userType"]:radio').each(function() {   
            if (this.value == '1'){   
               this.checked = true;   
            }       
         });   
	}

	function editUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "userMng/getUserInfo.do",
				data : {
					userId : row.USERID
				},
				async : true,
				success : function(data) {
					$('#dlg').dialog('open').dialog('setTitle', '修改用户');
					$('#fm').form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	function delUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选用户么?', function(r) {
				if (r) {
					$.post('userMng/delUser.do', {
						userId : row.USERID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#dg').datagrid('reload',getData4user()); // reload the user data
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
					$('#dg').datagrid('reload',getData4user()); // reload the user data
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

	function formatSex(val, row) {
		if (val == 1) {
			return '男';
		} else if (val == 0) {
			return '女';
		} else {
			return '未知';
		}
	}

	function formatUserType(val, row) {
		if (val == 1) {
			return '系统管理员';
		} else {
			return '教师';
		}
	}
	
	function layoutUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 0) {
				$.messager.alert('提示', '当前用户已经处于无效状态，无需再次注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中用户设置为无效么?', function(r) {
				if (r) {
					$.post('userMng/layoutUser.do', {
						userId : row.USERID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '注销成功!');
							$('#dg').datagrid('reload',getData4user()); // reload the user data
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

	function unLayoutUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			if (row.ISVALID == 1) {
				$.messager.alert('提示', '当前用户已经处于有效状态，无需再次取消注销!');
				return;
			}
			$.messager.confirm('Confirm', '确认将选中用户设置为有效么?', function(r) {
				if (r) {
					$.post('userMng/unLayoutUser.do', {
						userId : row.USERID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '取消注销成功!');
							$('#dg').datagrid('reload',getData4user()); // reload the user data
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

	function getData4user() {
		$.post('userMng/queryUserList.do?' + Math.random(), $('#ff')
				.serializeObject(), function(data) {
			$('#dg').datagrid({
				loadFilter : pagerFilter4user
			}).datagrid('loadData', data);
		});
	}

	function pagerFilter4user(data) {
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
		getData4user();
		
		
		$('#ccOrgan')
		.combobox(
				{onSelect : function() {
						$('#ccDept').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#ccOrgan').combobox('getValue');
						$('#ccDept').combobox(
								'reload', url);
					}
				});
		
		$('#organ')
		.combobox(
				{onSelect : function() {
						$('#dept').combobox('clear');
						$('#username').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#organ').combobox('getValue');
						$('#dept').combobox('reload', url);
						$('#username').combobox('reload', 'userMng/queryUser4sel.do?organId='
								+ $('#organ').combobox('getValue')+'&deptId=');
					}
				});
		
		$('#dept')
		.combobox(
				{onSelect : function() {
						$('#username').combobox('clear');
						$('#username').combobox('reload', 'userMng/queryUser4sel.do?deptId='
								+ $('#dept').combobox('getValue')+'&organId=');
					}
				});


	})
	
	$.extend($.fn.validatebox.defaults.rules, {    
    phoneNum: { //验证手机号   
        validator: function(value, param){ 
         return /(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value);
        },    
        message: '请输入正确的手机号码'   
    }
	});
    
</script>
<form id="ff" method="post" style="margin-top: 20px;">
    <div style="margin-bottom: 7px">
    	<label for="userNum">用户编号:</label>
		<input class="easyui-textbox" type="text" name="userNum"  style="width:200px;height:30px;"/>
		     	<label for="organId">所属学院:</label>
		<input id="organ" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false">
     	<label>所属系部:</label>
		<input id="dept" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do',editable:false">
		<label for="username">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
		<input class="easyui-combobox" type="text" name="userName"  style="width:200px;height:30px;"
			data-options="valueField:'str',textField:'username',url:'userMng/queryUser4sel.do'" id="username"/>
    </div>
     <div style="margin-bottom: 7px;">
		<label for="email">电子邮箱:</label>
		<input class="easyui-textbox" type="text" name="email" style="width:200px;height:30px;"/>
		<label for="mobile">移动电话:</label>
		<input class="easyui-textbox" type="text" name="mobile" style="width:200px;height:30px;"/>
		<label>是否有效:&nbsp;&nbsp;</label>
        <span class="radioSpan">
                <input type="radio" name="isValid" value="1">是</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isValid" value="0">否</input>
        </span>
        <label style="margin-left: 130px;">用户类型:&nbsp;&nbsp;</label>
        <span class="radioSpan">
            <input type="radio" name="usertype" value="1"/>系统管理员&nbsp;&nbsp;&nbsp;
			<input type="radio" name="usertype" value="2"/>教师
        </span>
        </div>
       <div style="margin-bottom: 7px;">
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:820px " onclick="doSearch4ff()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm4ff()"/>
    </div>
    
</form>
<table id="dg" title="用户列表" 
	style="width: 1080px; height: 73%;" toolbar="#toolbar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERID"  hidden="true">USERID</th>
			<th field="USERNUM" width="40px;" align="center">用户编号</th>
			<th field="USERNAME" width="40px;">姓名</th>
			<th field="SEX"  formatter="formatSex" width="20px;" align="center">性别</th>
			<th field="EMAIL" width="60px;">电子邮箱</th>
			<th field="MOBILE" width="40px;" align="center">移动电话</th>
			<th field="ORGANNAME" width="60px;">所属学院</th>
			<th field="DEPTNAME" width="60px;">所属系部</th>
			<th field="USERTYPE" formatter="formatUserType" width="30px;">用户类型</th>
			<th field="ISVALID"  formatter="formatValue" align="center" width="20px;">是否有效</th>
			<th field="BIRTHDAY" align="center" width="30px;">生日</th>
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
		closed="true" buttons="#dlg-buttons" modal="true">
	<form id="fm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="userId" hidden="true"/>
			<label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
			<input name="userName" class="easyui-textbox" required="true" style="width:200px;height:30px;" >
			<label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
            <span class="radioSpan">
                <input type="radio" name="sex" value="1" >男</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="0">女</input>
            </span>
 		</div>
		<div style="margin-bottom: 7px;">
			<label>电子邮箱</label>
			<input name="email" class="easyui-textbox" data-options="required:true,validType:'email' " style="width:200px;height:30px;"
			>
			<label>移动电话</label>
			<input name="mobile" class="easyui-textbox" style="width:200px;height:30px;" data-options="required:true,validType:'phoneNum'">
		</div>
		<div  style="margin-bottom: 7px;">
			<label>所属学院</label>
			<input id="ccOrgan" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false "
    			>
    		<label>所属系部</label>
			<input id="ccDept" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do',editable:false ">
		</div>
		<div  style="margin-bottom: 7px;">
			<label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址</label>
			<input name="address" class="easyui-validatebox"  style="width:455px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>生日日期</label> <input type="text" class="easyui-datebox"
				style="width: 200px; height: 30px;" name="birhtday"> 
			<label>用户类型:</label>
		
			<input type="radio" name="userType" value="1" checked="true" id="usertype"/>系统管理员&nbsp;&nbsp;&nbsp;
			<input type="radio" name="userType" value="2"/>教师
			
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>