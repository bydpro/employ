<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<%@ page language="java" import="srmt.java.common.Constants"%>
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

	function clearForm() {
		$('#queryProjectForm').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();
		
	    $("#projectFile").uploadify({
	        height        : 30,
	        swf           : './uploadify/uploadify.swf',
	        uploader      : 'research/saveFile.do',
	        width  :420,
            auto  : true,
            fileDataName:'file',
            buttonText : '选择需要上传的文件...',
            fileTypeExts : '*.doc; *.docx; *.pdf',
            multi    : false,
            method   :'post',
            fileSizeLimit : '200000KB',
            uploadLimit : 1,
            removeCompleted : false,
            debug:true,
            onCancel :  function (event, ID, fileObj, data ){
            	alert( "cancle" )
            	},
            onUploadStart : function(file) {
                var param = {};
                param.picHref = $('#file_upload_href').val();
                     $("#projectFile").uploadify("settings", "formData", param);
                },
            onUploadSuccess : function(file, data, response) {
            	var flag =JSON.parse(data);
                $("#projectFileUrl").val(flag.url);        
            },
            onUploadError : function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
               } 
	    });

	})
	
		function getData() {
		$.post('research/queryProjectList.do?' + Math.random(), $('#queryProjectForm').serializeObject(), function(data) {
			$('#projectDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#projectDg');
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
	
	function delProject() {
		var row = $('#projectDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选项目信息么?', function(r) {
				if (r) {
					$.post('research/delResearch.do', {
						rid : row.RID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#projectDg').datagrid('reload',getData());// reload the user data
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
	
	
	function addProjec() {
		$('#projectDlg').dialog('open').dialog('setTitle', '新增项目');
		$('#projectForm').form('clear');
		$("#projectDown").remove();
	}
	
	function saveProject() {
		$('#projectForm').form('submit', {
			url : "research/saveProject.do",
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
					debugger
					$.messager.alert('提示', '保存成功!');
					$('#projectDlg').dialog('close'); // close the dialog
					$('#projectDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function editProject() {
		var row = $('#projectDg').datagrid('getSelected');
		if (row) {
			var openDlg='';
			var url = '';
			var openForm = '';
			openDlg = '#projectDlg';
			url = 'research/getProjectInfo.do';
			openForm = '#projectForm';
			$.ajax({
				type : "POST",
				dataType : "json",
				url : url,
				data : {
					researchId : row.PROJECTID
				},
				async : true,
				success : function(data) {
					$(openDlg).dialog('open').dialog('setTitle', '修改');
					$(openForm).form('clear');
					$(openForm).form('load', data);
					$("#projectDown").remove();
					if (data.projectFile) {
						$("#projectPath")
								.append(
										"<div  style='margin-bottom: 7px;' align='center' id='projectDown'><a href=research/download.do?path="
												+ data.projectFile
												+ ">点击下载项目文件</a></div>");
					}
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
</script>
<form id="queryProjectForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="projectName">项目名称:</label> <input class="easyui-textbox"
			type="text" name="projectName" style="width: 200px; height: 30px;" />
		<label>项目类别：</label>
		<input id="projectType" class="easyui-combobox" name="projectType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryProjectType.do'">	
		<label>项目起始时间：</label>
		<input name="startTime" type="text"  style="width:170px;height:30px;" class="easyui-datebox" />
		<label>项目结束时间：</label>
		<input name="endTime" type="text" style="width:170px;height:30px;" class="easyui-datebox"/>
	</div>
	<div style="margin-bottom: 7px;">
	
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 845px"
			onclick="doSearch()"> 
		<input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm()" />
	</div>

</form>
<table id="projectDg" title="项目信息列表" 
	style="width: 1050px; height: 79%;" toolbar="#projectDgBar" data-options="
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
			<th field="PROJECTNAME" width="50">项目名称</th>
			<th field="PROJECTTYPE" width="50">项目类型</th>
			<th field="PROJECTFUND" width="50">项目经费</th>
			<th field="STARTTIME" width="50">开始日期</th>
			<th field="ENDTIME" width="50">开始日期</th>
			<th field="PROJECTSOURCE" width="50">项目来源</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="projectDgBar">
	<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="addProjec()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editProject()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delProject()">移除</a>
</div>
	<div id="projectDlg" class="easyui-dialog" style="width:630px;height:540px;padding:10px 20px"
		closed="true" buttons="#projectDlg-buttons" modal="true">
	<form id="projectForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="projectId" hidden="true"/>
			<label>项目名称：</label>
			<input name="projectName" class="easyui-validatebox" required="true" style="width:470px;height:30px;">
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>项目来源：</label>
			<input name="projectSource" class="easyui-validatebox" data-options="required:true" style="width:470px;height:30px;">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>项目类别：</label>
			<input id="projectType" class="easyui-combobox" name="projectType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryProjectType.do',editable:false" required="true" >
			<label>到位经费：</label>
			<input name="projectFund" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'￥'"  
					style="width:200px;height:30px;" required="true" >
		</div>
		<div style="margin-bottom: 7px;">
			<label>项目起始时间：</label>
			<input name="startTime" style="width:175px;height:30px;" class="easyui-datebox" required="true">
			<label>项目结束时间：</label>
			<input name="endTime" style="width:175px;height:30px;" class="easyui-datebox">
		</div>
		<div style="margin-bottom: 7px;">
			<label>项目内容：</label>
			<input name="projectContent" style="width:465px;height: 100px;" class="easyui-textbox" data-options="multiline:true" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>第&nbsp;&nbsp;一&nbsp;&nbsp;位：</label>
			<input name="projectFirst" class="easyui-validatebox"  style="width:200px;height:30px;" required="true" >
			<label>第&nbsp;&nbsp;二&nbsp;&nbsp;位：</label>
			<input name="projectSecond" class="easyui-validatebox"  style="width:200px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>第&nbsp;&nbsp;三&nbsp;&nbsp;位：</label>
			<input name="projectThird" class="easyui-validatebox"  style="width:200px;height:30px;">
		</div>
		<div style="margin-bottom: 7px; " id="projectPath">
			<input type="file" name="projectFile" id="projectFile" width="360px"/>
			<input name="projectFileUrl" hidden="true" id="projectFileUrl"/>
		</div>
	</form>
</div>
<div id="projectDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveProject()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#projectDlg').dialog('close')">取消</a>
</div>

