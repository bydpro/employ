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

	function clearQueryThesisForm() {
		debugger
		$('#queryThesisForm').form('clear');
	}

	function doQueryThesisSearch() {
		getData4queryThesis();
	}

	$(function() {
		getData4queryThesis();
		
		$('#organ4thesis')
		.combobox(
				{onSelect : function() {
						$('#dept4thesis').combobox('clear');
						$('#username4thesis').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#organ4thesis').combobox('getValue');
						$('#dept4thesis').combobox('reload', url);
						$('#username4thesis').combobox('reload', 'userMng/queryUser4sel.do?organId='
								+ $('#organ4thesis').combobox('getValue')+'&deptId=');
					}
				});
		
		$('#dept4thesis')
		.combobox(
				{onSelect : function() {
						$('#username4thesis').combobox('clear');
						$('#username4thesis').combobox('reload', 'userMng/queryUser4sel.do?deptId='
								+ $('#dept4thesis').combobox('getValue')+'&organId=');
					}
				});

	});
	
		function getData4queryThesis() {
		$.post('research/queryThesisList.do?' + Math.random(), $('#queryThesisForm').serializeObject(), function(data) {
			$('#thesisDg').datagrid({loadFilter : pagerFilter4queryThesis}).datagrid('loadData', data);
		});
	}

	function pagerFilter4queryThesis(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#thesisDg');
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
	
	function delThesis() {
		var row = $('#thesisDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选论文信息么?', function(r) {
				if (r) {
					$.post('research/delResearch.do', {
						rid : row.RID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#resTecDg').datagrid('reload',getData4queryThesis());// reload the user data
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
	
	function addThesis() {
		$('#thesisDlg').dialog('open').dialog('setTitle', '新增论文');
		$('#thesisForm').form('clear');
		$("#userStr4thesis").hide();
		$("#user4thesis").show();
		$("#thesisDown").remove();
	}
	
	function saveThesis() {
		var thesisPeriodical=$('#thesisPeriodical').combobox('getValues');
		$('#thesisPeriodicalStr').val(thesisPeriodical.join());
		$('#thesisForm').form('submit', {
			url : "research/saveThesis.do",
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
					$('#thesisDlg').dialog('close'); // close the dialog
					$('#thesisDg').datagrid('reload',getData4queryThesis()); // reload the user data
				}
			}
		});
	}
	


	function editThesis() {
		var row = $('#thesisDg').datagrid('getSelected');
		if (row) {
			openDlg = '#thesisDlg';
			url = 'research/getThesisInfo.do';
			openForm = '#thesisForm';
			$("#userStr4thesis").show();
			$("#user4thesis").hide();
			$("#userNumStr4thesis").html(row.USERNUM);
			$.ajax({
						type : "POST",
						dataType : "json",
						url : url,
						data : {
							researchId : row.THESISID
						},
						async : true,
						success : function(data) {
							debugger
							$(openDlg).dialog('open').dialog('setTitle', '修改');
							$(openForm).form('clear');
							var userNum = row.USERNUM;
							data.userNum = userNum;
							$(openForm).form('load', data);
							$("#thesisDown").remove();
							if (data.thesisFileUrl) {
								$("#thesisPath")
										.append(
												"<div  style='margin-bottom: 7px;' align='center' id='thesisDown'><a href=research/download.do?path="
														+ data.thesisFileUrl
														+ ">点击下载论文文件</a></div>")
							}
						},
					})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}

	$(function() {
		$("#thesisFile").uploadify(
				{
					height : 30,
					swf : './uploadify/uploadify.swf',
					uploader : 'research/saveFile.do',
					width : 520,
					auto : true,
					fileDataName : 'file',
					buttonText : '选择需要上传的论文文件...',
					fileTypeExts : '*.doc; *.docx; *.pdf',
					multi : false,
					method : 'post',
					fileSizeLimit : '200000KB',
					uploadLimit : 1,
					removeCompleted : false,
					debug : true,
					onCancel : function(event, ID, fileObj, data) {

					},
					onUploadStart : function(file) {
						var param = {};
						param.picHref = $('#file_upload_href').val();
						$("#thesisFile").uploadify("settings", "formData",
								param);
					},
					onUploadSuccess : function(file, data, response) {
						var flag = JSON.parse(data);
						$("#thesisFileUrl").val(flag.url);
					},
					onUploadError : function(file, errorCode, errorMsg,
							errorString) {
						alert('The file ' + file.name
								+ ' could not be uploaded: ' + errorString);
					}
				});
	});
	
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
<!-- url 直接指向文件地址，或返回正确的文件地址 -->
<form id="queryThesisForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="thesisName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
					<label for="organ4thesis">所属学院:</label>
		<input id="organ4thesis" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false">
     	<label>所属系部:</label>
		<input id="dept4thesis" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do',editable:false">
		<label for="username">教师姓名:</label>
		<input class="easyui-combobox" type="text" name="userName"  style="width:200px;height:30px;"
			data-options="valueField:'str',textField:'username',url:'userMng/queryUser4sel.do'" id="username4thesis"/>
	
	</div>
	<div style="margin-bottom: 7px;">	
		<label for="thesisName">论文名称:</label> <input class="easyui-textbox"
			type="text" name="thesisName" style="width: 200px; height: 30px;" />	
			<label >论文类型:</label>
			<input id="thesisType" class="easyui-combobox" name="thesisType" style="width: 200px; height: 30px;" 
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisType.do',editable:false">
    		<label>起始日期:</label>
			<input name="thesisStartDate" style="width:200px;height: 30px;" class="easyui-datebox" data-options="editable:false" >
			<label>结束日期:</label>
			<input name="thesisEndDate" style="width:200px;height: 30px;" class="easyui-datebox" data-options="editable:false" >
	</div>
	<div style="margin-bottom: 7px;">	
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 842px"
			onclick="doQueryThesisSearch()"> <input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearQueryThesisForm()" />
	</div>

</form>
<table id="thesisDg" title="论文信息列表" 
	style="width: 1120px; height: 74%;" toolbar="#thesisDgBar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNUM" width="52px" align="center">用户编号</th>
			<th field="USERNAME" width="30px">姓名</th>
			<th field="THESISNAME" width="60px">论文名称</th>
			<th field="THESISTYPE" width="30px" align="center">论文类型</th>
			<th field="THESISAUTHOR" width="50px">全部作者</th>
			<th field="THESISRECORD" width="60px">发表期刊</th>
			<th field="THESISPAGE" width="50px">发表卷、期、页码</th>
			<th field="THESISDATE" width="40px">发表日期</th>
			<th field="THESISPERIODICAL" width="100px">论文收录情况</th>
			<th field="THESISPASS" width="50" align="center" formatter="format">审核状态 </th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="thesisDgBar">
	<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="addThesis()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editThesis()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delThesis()">移除</a>
</div>
<div id="thesisDlg" class="easyui-dialog" style="width:590px;height:590px;padding:10px 20px"
		closed="true" buttons="#thesisDlg-buttons" align="center" modal="true">
	<form id="thesisForm" method="post" enctype="multipart/form-data" >
		<div  style="margin-bottom: 7px;">
			<div id="userStr4thesis" align="left">
				<label style="margin-left: 25px;">用户编号：</label>
				<label id="userNumStr4thesis" style="margin-left: 120px;"></label>
			</div>
			<div id="user4thesis">
				<label style="margin-right: 120px;">用户编号：</label>
				<input name="userNum" id ="thesis4userNum" class="easyui-numberbox" required="true" style="width:300px;height:30px;"  data-options="precision:0,min :201610000001,max:999999999999">
			</div>
		</div>
		<div  style="margin-bottom: 7px;">
			<input name="thesisId" hidden="true"/>
			<label>论文名称：</label>
			<input name="thesisName" class="easyui-validatebox" required="true" style="width:300px;height:30px;margin-left: 120px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label style="margin-right: 120px;">论文类型：</label>
			<input id="thesisType" class="easyui-combobox" name="thesisType" style="width:300px;height:30px;margin-left:120px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisType.do',editable:false" required="true" >
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>全部作者(","号隔开)：</label>
			<input name="thesisAuthor" class="easyui-validatebox" data-options="required:true" style="width:300px;height:30px;margin-left: 60px;">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>论文收录情况（SCI、EI、ISTP)：</label>
		    <input id="thesisPeriodical" class="easyui-combobox" name="thesisPeriodical" style="width:300px;height:30px;margin-left: 120px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisIncluded.do',multiple:true,editable:false" required="true" >
		 	<input id="thesisPeriodicalStr" name="thesisPeriodicalStr" hidden="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>发表期刊：</label>
			<input name="thesisRecord" style="width:300px;height: 30px;margin-left: 120px;" class="easyui-validatebox" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>发表卷、期、页码</label>
			<input name="thesisPage" style="width:170px;height: 30px;" class="easyui-textbox" required="true" data-options="prompt:'2007, 24(12)'">
			<label>发表日期</label>
			<input name="thesisDate" style="width:160px;height: 30px;" class="easyui-datebox" data-options="editable:false" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label style="margin-right: 120px;">论文摘要：</label>
			<input name="thesisAbstract" style="width:300px;height: 100px;" class="easyui-textbox" data-options="multiline:true">
		</div>
		<div style="margin-bottom: 7px; " align="left" id="thesisPath">
			<input type="file" name="thesisFile" id="thesisFile" width="360px"/>
			<input name="thesisFileUrl" hidden="true" id="thesisFileUrl"/>
		</div>
	</form>
</div>
<div id="thesisDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesis()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#thesisDlg').dialog('close')">取消</a>
</div>