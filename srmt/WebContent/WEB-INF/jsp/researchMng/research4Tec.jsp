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
		$('#resTecFf').form('clear');
	}

	function doSearch() {
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('research/queryResearchList.do?' + Math.random(), $('#resTecFf').serializeObject(), function(data) {
			$('#resTecDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#resTecDg');
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
	
	function delResearch() {
		var row = $('#resTecDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选科研信息么?', function(r) {
				if (r) {
					$.post('research/delResearch.do', {
						rid : row.RID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#resTecDg').datagrid('reload',getData());// reload the user data
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
	}
	
	function addReward() {
		$('#rewardDlg').dialog('open').dialog('setTitle', '新增奖励');
		$('#rewardForm').form('clear');
	}
	
	function addProjec() {
		$('#projectDlg').dialog('open').dialog('setTitle', '新增项目');
		$('#projectForm').form('clear');
	}
	
	function addPatent() {
		$('#patentDlg').dialog('open').dialog('setTitle', '新增专利');
		$('#patentForm').form('clear');
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
					$('#resTecDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
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
					$('#resTecDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function savePatent() {
		$('#patentForm').form('submit', {
			url : "research/savePatent.do",
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
					$('#patentDlg').dialog('close'); // close the dialog
					$('#resTecDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
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
					$.messager.alert('提示', '保存成功!');
					$('#projectDlg').dialog('close'); // close the dialog
					$('#resTecDg').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function editResearch() {
		var row = $('#resTecDg').datagrid('getSelected');
		if (row) {
			debugger
			var openDlg='';
			var url = '';
			var openForm = '';
			var researchType = row.RESEARCHTYPE;
			<%String thesisType=Constants.RESEARCH_TYPE_THESIS;%>
			<%String patentType=Constants.RESEARCH_TYPE_PATENT;%>
			<%String rewardType=Constants.RESEARCH_TYPE_REWARD;%>
			<%String projectType=Constants.RESEARCH_TYPE_PROJECT;%>
			var thesisType ='<%=thesisType%>';
			var patentType ='<%=patentType%>';
			var rewardType ='<%=rewardType%>';
			var projectType='<%=projectType%>';
			if(researchType == thesisType){
				openDlg ='#thesisDlg';
				url ='research/getThesisInfo.do';
				openForm='#thesisForm';
			}else if(patentType==researchType){
				openDlg ='#patentDlg';
				url ='research/getPatentInfo.do';
				openForm='#patentForm';
			}else if(rewardType == researchType){
				openDlg ='#rewardDlg';
				url ='research/getRewardInfo.do';
				openForm='#rewardForm';
			}else {
				openDlg ='#projectDlg';
				url ='research/getProjectInfo.do';
				openForm='#projectForm';
			}
			$.ajax({
				type : "POST",
				dataType : "json",
				url : url,
				data : {
					researchId : row.RESEARCHID
				},
				async : true,
				success : function(data) {
					$(openDlg).dialog('open').dialog('setTitle', '修改');
					$(openForm).form('clear');
					$(openForm).form('load', data);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	
	$(function() {
	    $("#thesisFile").uploadify({
	        height        : 30,
	        swf           : './uploadify/uploadify.swf',
	        uploader      : 'research/saveFile.do',
	        width  :520,
            auto  : true,
            fileDataName:'file',
            buttonText : '选择需要上传的论文文件...',
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
                     $("#thesisFile").uploadify("settings", "formData", param);
                },
            onUploadSuccess : function(file, data, response) {
            	var flag =JSON.parse(data);
                $("#thesisFileUrl").val(flag.url);        
            },
            onUploadError : function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
               } 
	    });
	    
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
	    
	    $("#rewardFile").uploadify({
	        height        : 30,
	        swf           : './uploadify/uploadify.swf',
	        uploader      : 'research/saveFile.do',
	        width  :260,
            auto  : true,
            fileDataName:'file',
            buttonText : '选择需要上传的证书文件...',
            fileTypeExts : '*.doc; *.docx; *.pdf; *.zip',
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
                     $("#rewardFile").uploadify("settings", "formData", param);
                },
            onUploadSuccess : function(file, data, response) {
            	var flag =JSON.parse(data);
                $("#rewardFileUrl").val(flag.url);        
            },
            onUploadError : function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
               } 
	    });
	    
	    $("#patentFile").uploadify({
	        height        : 30,
	        swf           : './uploadify/uploadify.swf',
	        uploader      : 'research/saveFile.do',
	        width  :210,
            auto  : true,
            fileDataName:'file',
            buttonText : '选择需要上传的文件...',
            fileTypeExts : '*.doc; *.docx; *.pdf; *.zip',
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
                     $("#patentFile").uploadify("settings", "formData", param);
                },
            onUploadSuccess : function(file, data, response) {
            	var flag =JSON.parse(data);
                $("#patentFileUrl").val(flag.url);        
            },
            onUploadError : function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
               } 
	    });
	});
</script>
<form id="resTecFf" method="post">
     <div style="margin-bottom: 7px;">
    	 <label for="researchName">科研名称:</label>
		<input class="easyui-textbox" type="text" name="researchName"  style="width:200px;height:30px;"/>
		<label for="organName">科研类型:</label>
		<input id="cc" class="easyui-combobox" name="dictValue" style="width:200px;height:30px;"
    			data-options="valueField:'dictvalue',textField:'dictname',url:'research/queryResearchType.do'">
		<input class="easyui-linkbutton" type="button" value="查询" style="width:98px;height:30px;
				margin-left:200px " onclick="doSearch()">
		<input class="easyui-linkbutton" type="button" value="重置" style="width:98px;height:30px;" onclick="clearForm()"/>
    </div>
    
</form>
<table id="resTecDg" title="教师科研信息列表" 
	style="width: 1050px; height: 85%;" toolbar="#toolbar4resTec" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNAME" width="50">姓名</th>
			<th field="RESEARCHNAME" width="50">科研名称</th>
			<th field="DICTNAME" width="50">科研类型</th>
			<th field="MOBILE" width="50">移动电话</th>
			<th field="EMAIL" width="50">电子邮箱</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="toolbar4resTec">
	<a href="#" class="easyui-menubutton" plain="true" data-options="menu:'#mm2',iconCls:'icon-add'">新增</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editResearch()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delResearch()">移除</a>
</div>
<div id="mm2" style="width: 100px;">
	<div><a href="#" class="easyui-linkbutton"  plain="true" onclick="addThesis()">论&nbsp;&nbsp;&nbsp;文</a></div>
	<div><a href="#" class="easyui-linkbutton"  plain="true" onclick="addProjec()">项&nbsp;&nbsp;&nbsp;目</a></div>
	<div><a href="#" class="easyui-linkbutton"  plain="true" onclick="addPatent()">专&nbsp;&nbsp;&nbsp;利</a></div>
	<div><a href="#" class="easyui-linkbutton"  plain="true" onclick="addReward()">奖&nbsp;&nbsp;&nbsp;励</a></div>
</div>

<div id="thesisDlg" class="easyui-dialog" style="width:590px;height:580px;padding:10px 20px"
		closed="true" buttons="#thesisDlg-buttons" align="center">
	<form id="thesisForm" method="post" enctype="multipart/form-data" >
		<div  style="margin-bottom: 7px;">
			<input name="thesisId" hidden="true"/>
			<label>论文名称：</label>
			<input name="thesisName" class="easyui-validatebox" required="true" style="width:300px;height:30px;margin-left: 120px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label style="margin-right: 120px;">论文类别：</label>
			<input id="thesisType" class="easyui-combobox" name="thesisType" style="width:300px;height:30px;margin-left:120px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisType.do'" required="true" >
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>全部作者(","号隔开)：</label>
			<input name="thesisAuthor" class="easyui-validatebox" data-options="required:true" style="width:300px;height:30px;margin-left: 60px;">
 		</div>
		<div style="margin-bottom: 7px;">
			<label>论文收录情况（SCI、EI、ISTP)：</label>
		    <input id="thesisPeriodical" class="easyui-combobox" name="thesisPeriodical" style="width:300px;height:30px;margin-left: 120px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisIncluded.do',multiple:true" required="true" >
		 	<input id="thesisPeriodicalStr" name="thesisPeriodicalStr" hidden="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>发表期刊、卷、期、页码，日期：</label>
			<input name="thesisRecord" style="width:300px;height: 100px;" class="easyui-textbox" data-options="multiline:true" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label style="margin-right: 120px;">论文摘要：</label>
			<input name="thesisAbstract" style="width:300px;height: 100px;" class="easyui-textbox" data-options="multiline:true" required="true">
		</div>
		<div style="margin-bottom: 7px; " align="left">
			<input type="file" name="thesisFile" id="thesisFile" width="360px"/>
			<input name="thesisFileUrl" hidden="true" id="thesisFileUrl"/>
		</div>
	</form>
</div>
<div id="thesisDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesis()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#thesisDlg').dialog('close')">取消</a>
</div>

<div id="rewardDlg" class="easyui-dialog" style="width:590px;height:500px;padding:10px 20px"
		closed="true" buttons="#rewardDlg-buttons" align="center">
	<form id="rewardForm" method="post">
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
			<label>获奖类别：</label>
			<input id="rewardType" class="easyui-combobox" name="rewardType" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryRewardType.do'" required="true" >
			<label>本人位次：</label>
			<input id="rewardPlace" class="easyui-combobox" name="rewardPlace" style="width:200px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPlaceType.do'" required="true" >
		</div>
		<div style="margin-bottom: 7px;" align="left">

			<label>获奖时间：</label>
			<input name="rewardTime" style="width:200px;height:30px;" class="easyui-datebox">
		</div>
		<div style="margin-bottom: 7px; ">
			<input type="file" name="rewardFile" id="rewardFile" width="260px"/>
			<input name="rewardFileUrl" hidden="true" id="rewardFileUrl"/>
		</div>
	</form>
</div>
<div id="rewardDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveReward()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#rewardDlg').dialog('close')">取消</a>
</div>

<div id="projectDlg" class="easyui-dialog" style="width:600px;height:540px;padding:10px 20px"
		closed="true" buttons="#projectDlg-buttons">
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
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryProjectType.do'" required="true" >
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
		<div style="margin-bottom: 7px; ">
			<input type="file" name="projectFile" id="projectFile" width="360px"/>
			<input name="projectFileUrl" hidden="true" id="projectFileUrl"/>
		</div>
	</form>
</div>
<div id="projectDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveProject()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#projectDlg').dialog('close')">取消</a>
</div>


<div id="patentDlg" class="easyui-dialog" style="width:560px;height:530px;padding:10px 20px"
		closed="true" buttons="#patentDlg-buttons" align="center">
	<form id="patentForm" method="post">
		<div  style="margin-bottom: 7px;">
			<input name="patentId" hidden="true"/>
			<label>知识产权名称：</label>
			<input name="patentName" class="easyui-validatebox" required="true" style="width:300px;height:30px;">
		</div>
 		<div style="margin-bottom: 7px;">	
 			<label>知识产权类型：</label>
 			<input id="patentType" class="easyui-combobox" name="patentType" style="width:300px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentType.do'" required="true" >
 		</div>
 		 <div style="margin-bottom: 7px;">	
 			<label>专利人权类别：</label>
 			<input id="patentPeople" class="easyui-combobox" name="patentPeople" style="width:300px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentPeople.do'" required="true" >
 		</div>
 			<div style="margin-bottom: 7px;">
			<label>专利内容简介：</label>
			<input name="patentContent" style="width:300px;height: 100px;" class="easyui-textbox" data-options="multiline:true" required="true">
		</div>
		<div style="margin-bottom: 7px;">
			<label>发&nbsp;&nbsp;&nbsp;&nbsp;明&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;：</label>
			<input name="patentCreater" style="width:300px;height:30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>授&nbsp;&nbsp;权&nbsp;&nbsp;日&nbsp;&nbsp;期&nbsp;&nbsp;：</label>
			<input name="patentDate" style="width:150px;height:30px;" class="easyui-datebox">
			 <label style="margin-left: 20px;">是否首位：</label>
		    <span class="radioSpan">
                <input type="radio" name="patentFirst" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="patentFirst" value="0">否</input>
            </span>
		</div>
		<div  style="margin-bottom: 7px;" align="left">	   
             <label style="margin-left: 50px;">是&nbsp;&nbsp;否&nbsp;&nbsp;转&nbsp;&nbsp;让&nbsp;&nbsp;：</label>
		    <span class="radioSpan">
                <input type="radio" name="patentIsTransfer" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="patentIsTransfer" value="0">否</input>
            </span>
		</div>
		<div style="margin-bottom: 7px;" align="center">
			<input type="file" name="patentFile" id="patentFile"/>
			<input name="patentFileUrl" hidden="true" id="patentFileUrl"/>
		</div>
	</form>
</div>
<div id="patentDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePatent()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#patentDlg').dialog('close')">取消</a>
</div>