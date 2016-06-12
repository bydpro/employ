<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
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

	function clearqueryRewardForm() {
		$('#queryRewardForm').form('clear');
	}

	function doSearch4queryRewardForm() {
		getData4queryReward();
	}

	$(function() {
		getData4queryReward();

		$('#organ4reward')
		.combobox(
				{onSelect : function() {
						$('#dept4reward').combobox('clear');
						$('#username4reward').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#organ4reward').combobox('getValue');
						$('#dept4reward').combobox('reload', url);
						$('#username4reward').combobox('reload', 'userMng/queryUser4sel.do?organId='
								+ $('#organ4reward').combobox('getValue')+'&deptId=');
					}
				});
		
		$('#dept4reward')
		.combobox(
				{onSelect : function() {
						$('#username4reward').combobox('clear');
						$('#username4reward').combobox('reload', 'userMng/queryUser4sel.do?deptId='
								+ $('#dept4reward').combobox('getValue')+'&organId=');
					}
				});
		
		$("#rewardFile").uploadify(
				{
					height : 30,
					swf : './uploadify/uploadify.swf',
					uploader : 'research/saveFile.do',
					width : 260,
					auto : true,
					fileDataName : 'file',
					buttonText : '选择需要上传的证书文件...',
					fileTypeExts : '*.doc; *.docx; *.pdf; *.zip',
					multi : false,
					method : 'post',
					fileSizeLimit : '200000KB',
					uploadLimit : 1,
					removeCompleted : false,
					debug : true,
					onCancel : function(event, ID, fileObj, data) {
						alert("cancle")
					},
					onUploadStart : function(file) {
						var param = {};
						param.picHref = $('#file_upload_href').val();
						$("#rewardFile").uploadify("settings", "formData",
								param);
					},
					onUploadSuccess : function(file, data, response) {
						var flag = JSON.parse(data);
						$("#rewardFileUrl").val(flag.url);
					},
					onUploadError : function(file, errorCode, errorMsg,
							errorString) {
						alert('The file ' + file.name
								+ ' could not be uploaded: ' + errorString);
					}
				});

	})

	function getData4queryReward() {
		$.post('research/queryRewardList.do?' + Math.random(), $(
				'#queryRewardForm').serializeObject(), function(data) {
			$('#rewardDg').datagrid({
				loadFilter : pagerFilter4queryReward
			}).datagrid('loadData', data);
		});
	}

	function pagerFilter4queryReward(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#rewardDg');
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

	function delReward() {
		var row = $('#rewardDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选奖励信息么?', function(r) {
				if (r) {
					$.post('research/delResearch.do', {
						rid : row.RID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#rewardDg').datagrid('reload', getData4queryReward());// reload the user data
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

	function saveReward() {
		$('#rewardForm').form('submit', {
			url : "research/saveReward.do",
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
					$('#rewardDlg').dialog('close'); // close the dialog
					$('#rewardDg').datagrid('reload', getData4queryReward()); // reload the user data
					
				}
			}
		});
	}

	function addReward() {
		$('#rewardDlg').dialog('open').dialog('setTitle', '新增奖励');
		$('#rewardForm').form('clear');
		$("#userStr4reward").hide();
		$("#user4reward").show();
		$("#rewardDown").remove();
	}
	function editReward() {
		var row = $('#rewardDg').datagrid('getSelected');
		if (row) {
			var openDlg = '';
			var url = '';
			var openForm = '';
			openDlg = '#rewardDlg';
			url = 'research/getRewardInfo.do';
			openForm = '#rewardForm';
			$("#userStr4reward").show();
			$("#user4reward").hide();
			$("#userNumStr4reward").html(row.USERNUM);
			$.ajax({
				type : "POST",
				dataType : "json",
				url : url,
				data : {
					researchId : row.REWARDID
				},
				async : true,
				success : function(data) {
					$(openDlg).dialog('open').dialog('setTitle', '修改');
					$(openForm).form('clear');
					$(openForm).form('load', data);
					$("#rewardDown").remove();
					if (data.rewardFile) {
						$("#rewardPath")
								.append(
										"<div  style='margin-bottom: 7px;' align='center' id='rewardDown'><a href=research/download.do?path="
												+ data.rewardFile
												+ ">点击下载奖励文件</a></div>");
					}
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	
	function format(val, row) {
		if (val == 1) {
			return '审核通过';
		} else if (val == 2) {
			return '审核不通过';
		} else if (val == 3) {
			return '未审核';
		}
	}
</script>
<form id="queryRewardForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="projectName">用户编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
			<label for="organ4reward">所属学院:</label>
		<input id="organ4reward" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false">
     	<label>所属系部:</label>
		<input id="dept4reward" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do',editable:false">
		<label for="username">教师姓名:</label>
		<input class="easyui-combobox" type="text" name="userName"  style="width:200px;height:30px;"
		
			data-options="valueField:'str',textField:'username',url:'userMng/queryUser4sel.do'" id="username4reward"/>
	</div>
	<div style="margin-bottom: 7px;">
		<label for="rewardName">奖励名称:</label> <input class="easyui-textbox"
			type="text" name="rewardName" style="width: 200px; height: 30px;" />
		<label>获奖类型:</label>
		<input id="rewardType" class="easyui-combobox" name="rewardType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryRewardType.do',editable:false ">
		<label for="rewardOrgan">奖励单位:</label> 
		<input class="easyui-textbox"	type="text" name="rewardOrgan" style="width: 200px; height: 30px;" />

		<label>	获奖时间:</label>
		<input name="rewardTime" type="text" style="width:200px;height:30px;" class="easyui-datebox" data-options="editable:false"/>
	</div>
	<div style="margin-bottom: 7px;">
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 845px"
			onclick="doSearch4queryRewardForm()"> 
		<input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearqueryRewardForm()" />
	</div>

</form>
<table id="rewardDg" title="奖励信息列表" 
	style="width: 1050px; height: 73%;" toolbar="#rewardDgBar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNUM" width="50">用户编号</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="REWARDNAME" width="50">奖励名称</th>
			<th field="REWARDTYPE" width="50">奖励类别</th>
			<th field="REWARDORGAN" width="50">奖励单位</th>
			<th field="REWARDUSER" width="50">获奖所有人员</th>
			<th field="REWARDTIME" width="50">获奖时间</th>
			<th field="REWARDPASS" width="50" align="center" formatter="format">审核状态 </th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="rewardDgBar">
	<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="addReward()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editReward()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delReward()">移除</a>
</div>

<div id="rewardDlg" class="easyui-dialog" style="width:610px;height:500px;padding:10px 20px"
		closed="true" buttons="#rewardDlg-buttons" align="center" modal="true">
	<form id="rewardForm" method="post">
		<div style="margin-bottom: 7px;">
			<div id="userStr4reward" align="left">
				<label style="margin-left: 5px;">用户编号：</label>
				<label id="userNumStr4reward"></label>
			</div>
			<div id="user4reward">
				<label>用户编号：</label> <input name="userNum" class="easyui-numberbox"
					required="true" style="width: 460px;; height: 30px;"
					data-options="precision:0,min :201610000001,max:999999999999">
			</div>
		</div>
		<div  style="margin-bottom: 7px;">
			<input name="rewardId" hidden="true"/>
			<label>获奖名称：</label>
			<input name="rewardName" class="easyui-validatebox" required="true" style="width:460px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>获奖机关：</label>
			<input name="rewardOrgan" style="width:460px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>获奖所有人员(","号隔开)：</label>
			<input name="rewardUser" style="width:380px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>获奖简介：</label>
			<input name="rewardContent" style="width:460px;height: 100px;" class="easyui-textbox" data-options="multiline:true" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>获奖类型：</label>
			<input id="rewardType" class="easyui-combobox" name="rewardType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryRewardType.do',editable:false " required="true" >
			<label>本人位次：</label>
			<input id="rewardPlace" class="easyui-combobox" name="rewardPlace" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPlaceType.do',editable:false " required="true" >
		</div>
		<div style="margin-bottom: 7px;" align="left">

			<label>获奖时间：</label>
			<input name="rewardTime" style="width:200px;height:30px;" class="easyui-datebox">
		</div>
		<div style="margin-bottom: 7px; " id="rewardPath">
			<input type="file" name="rewardFile" id="rewardFile" width="260px"/>
			<input name="rewardFileUrl" hidden="true" id="rewardFileUrl"/>
		</div>
	</form>
</div>
<div id="rewardDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReward()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#rewardDlg').dialog('close')">取消</a>
</div>