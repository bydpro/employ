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

	function formatValue(val, row) {
		if (val == 1) {
			return '是';
		} else {
			return '否';
		}
	}
	
	function clearForm4patent() {
		$('#queryPatentForm').form('clear');
	}

	function getData4patent() {
		$.post('research/queryPatentList.do?' + Math.random(), $('#queryPatentForm').serializeObject(), function(data) {
			$('#patentDg').datagrid({loadFilter : pagerFilter4patent}).datagrid('loadData', data);
		});
	}

	function pagerFilter4patent(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#patentDg');
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
	
	function doSearch4patent() {
		getData4patent();
	}

	$(function() {
		getData4patent();
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
					$('#patentDg').datagrid('reload',getData4patent()); // reload the user data
				}
			}
		});
	}
	
	
	function addPatent() {
		$('#patentDlg').dialog('open').dialog('setTitle', '新增专利');
		$('#patentForm').form('clear');
		$("#patentDown").remove();
	}
	
	function delPatent() {
		var row = $('#patentDg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选专利信息么?', function(r) {
				if (r) {
					$.post('research/delResearch.do', {
						rid : row.RID
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#patentDg').datagrid('reload',getData4patent());// reload the user data
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
	
	
	function editPatent() {
		var row = $('#patentDg').datagrid('getSelected');
		if (row) {
			var openDlg='';
			var url = '';
			var openForm = '';
			openDlg ='#patentDlg';
			url ='research/getPatentInfo.do';
			openForm='#patentForm';

			$.ajax({
				type : "POST",
				dataType : "json",
				url : url,
				data : {
					researchId : row.PATENTID
				},
				async : true,
				success : function(data) {
					$(openDlg).dialog('open').dialog('setTitle', '修改');
					$(openForm).form('clear');
					$(openForm).form('load', data);
					$("#patentDown").remove();
					if (data.patentFile) {
						$("#patentPath")
								.append(
										"<div  style='margin-bottom: 7px;' align='center' id='patentDown'><a href=research/download.do?path="
												+ data.patentFile
												+ ">点击下载专利文件</a></div>");
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

<form id="queryPatentForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="patentName">知识产权名称:</label> <input class="easyui-textbox"
			type="text" name="patentName" style="width: 180px; height: 30px;" />
		 <label>知识产权类型:</label>
 		<input  class="easyui-combobox" name="patentType" style="width:180px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentType.do'">
		<label>专利人权类别:</label>
 		<input id="patentPeople" class="easyui-combobox" name="patentPeople" style="width:180px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentPeople.do'">
    	<label>授权日期:</label>
		<input name="patentDate" style="width:180px;height:30px;" class="easyui-datebox">
	</div>
	<div style="margin-bottom: 7px;">

		 <label>是&nbsp;&nbsp;否&nbsp;&nbsp;首&nbsp;&nbsp;位：</label>
		    <span class="radioSpan">
                <input type="radio" name="patentFirst" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="patentFirst" value="0">否</input>
            </span>
		 <label style="margin-left: 118px;">是&nbsp;&nbsp;否&nbsp;&nbsp;转&nbsp;&nbsp;让&nbsp;&nbsp;：</label>
		    <span class="radioSpan">
                <input type="radio" name="patentIsTransfer" value="1">是</input>&nbsp;&nbsp;&nbsp;
                <input type="radio" name="patentIsTransfer" value="0">否</input>
            </span>
		<input class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left:410px"
			onclick="doSearch4patent()"> 
		<input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm4patent()" />
	</div>

</form>
<table id="patentDg" title="专利信息列表" 
	style="width: 1050px; height: 78%;" toolbar="#patentDgBar" data-options="
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
			<th field="PATENTNAME" width="50">知识产权名称</th>
			<th field="PATENTTYPE" width="50">知识产权类型</th>
			<th field="PATENTPEOPLE" width="50">专利人权类别</th>
			<th field="PATENTCREATER" width="50">发明人</th>
			<th field="PATENTFIRST" width="50" formatter="formatValue" align="center">是否首位</th>
			<th field="PATENTISTRANSFER" width="50" formatter="formatValue" align="center">是 否转让</th>
			<th field="PATENTDATE" width="50" align="center">授权日期 </th>
			<th field="PATENTPASS" width="50" align="center" formatter="format">审核状态 </th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>
<div id="patentDgBar">
	<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="addPatent()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPatent()">修改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delPatent()">移除</a>
</div>


<div id="patentDlg" class="easyui-dialog" style="width:560px;height:530px;padding:10px 20px"
		closed="true" buttons="#patentDlg-buttons" align="center" modal="true">
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
		<div style="margin-bottom: 7px;" align="center" id="patentPath">
			<input type="file" name="patentFile" id="patentFile"/>
			<input name="patentFileUrl" hidden="true" id="patentFileUrl"/>
		</div>
	</form>
</div>
<div id="patentDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePatent()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#patentDlg').dialog('close')">取消</a>
</div>