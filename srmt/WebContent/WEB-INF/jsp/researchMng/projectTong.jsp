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
		$('#queryProjectTongForm').form('clear');
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
                     $("#proectFile").uploadify("settings", "formData", param);
                },
            onUploadSuccess : function(file, data, response) {
            	var flag =JSON.parse(data);
                $("#proectFileUrl").val(flag.url);        
            },
            onUploadError : function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
               } 
	    });

	})
	
		function getData() {
		$.post('research/queryProjecTongtList.do?' + Math.random(), $('#queryProjectTongForm').serializeObject(), function(data) {
			$('#projectTongDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
			$("#size4project").html(data.length);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#projectTongDg');
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
</script>
<form id="queryProjectTongForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="projectName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
		<label for="projectName">教师姓名:</label> <input class="easyui-textbox"
			type="text" name="userName" style="width: 200px; height: 30px;" />
		<label for="projectName">项目名称:</label> <input class="easyui-textbox"
			type="text" name="projectName" style="width: 200px; height: 30px;" />
		<label>项目类别：</label>
		<input id="projectType" class="easyui-combobox" name="projectType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryProjectType.do'">	
	
	</div>
	<div style="margin-bottom: 7px;">
		<label>项目起始时间：</label>
		<input name="startTime" type="text"  style="width:170px;height:30px;" class="easyui-datebox" />
		<label>项目结束时间：</label>
		<input name="endTime" type="text" style="width:170px;height:30px;" class="easyui-datebox"/>
		<input class="easyui-linkbutton" type="button" value="检索"
			style="width: 198px; height: 30px; margin-left: 295px"
			onclick="doSearch()"> 
	</div>

</form>
<div style="color: blue;"><font size="5">共检索到</font> <font id="size4project" size="5"></font><font size="5">条记录</font></div>
<table id="projectTongDg" title="项目信息统计列表" 
	style="width: 1050px; height: 72%;" toolbar="#projectTongDgBar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:20">
	<thead>
		<tr>
			<th field="USERNUM" width="50">教师编号</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="PROJECTNAME" width="50">项目名称</th>
			<th field="PROJECTTYPE" width="50">项目类型</th>
			<th field="PROJECTFUND" width="50">项目经费</th>
			<th field="STARTTIME" width="50">开始日期</th>
			<th field="ENDTIME" width="50">开始日期</th>
			<th field="PROJECTSOURCE" width="50">项目来源</th>
			<th field="workload" width="50">项目科研分</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
