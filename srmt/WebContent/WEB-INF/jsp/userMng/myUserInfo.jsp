<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "userMng/getMyUserInfo.do",
			async : true,
			success : function(data) {
				$("#showPic").attr("src", data.picPath);
				$('#myUserForm').form('load', data);

			},
		});

		$("#errorMsg").text("");
		$("#errorMsg").hide();
		
		$('#organId')
		.combobox(
				{onSelect : function() {
						$('#dept').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#organId').combobox('getValue');
						$('#dept').combobox(
								'reload', url);
					}
				});
	});

	function saveMyUser() {
		$('#myUserForm').form('submit', {
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
					$.messager.alert({
						msg : '保存成功'
					});
				}
			}
		});
	}

	function resetPass() {
		$("#errorMsg").text("");
		$("#errorMsg").hide();
		$('#fm4MyUser').form('clear');
		$('#dlg4MyUser').dialog('open').dialog('setTitle', '修改密码');
	}

	function savePass() {

		$('#fm4MyUser').form('submit', {
			url : "userMng/savePassword.do",
			onSubmit : function() {
				if (checkPass()) {
					return false;
				}
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
					$.messager.alert({
						msg : '修改成功'
					});
					$('#dlg4MyUser').dialog('close');
				}
			}
		});
	}

	function checkPass() {
		var newPass = $('#newPassword').val();
		var newPassword4Check = $('#newPassword4Check').val();
		if (newPass != newPassword4Check) {
			$("#errorMsg").text("两次密码不一致");
			$("#errorMsg").show("slow");
			return true;
		} else {
			return false;
		}
	}

	function showPic() {
		debugger
		var url = $("#pic").val();
		$("#pic").attr("src", url);
	}

	$("#pic")
			.uploadify(
					{
						height : 30,
						swf : './uploadify/uploadify.swf',
						uploader : 'research/saveFile.do',
						width : 210,
						auto : true,
						fileDataName : 'file',
						buttonText : '修改头像',
						fileTypeExts : '*.jpg; *.bmp; *.gif; *.png',
						multi : false,
						method : 'post',
						fileSizeLimit : '200000KB',
						uploadLimit : 1,
						removeCompleted : false,
						debug : true,
						onCancel : function(event, ID, fileObj, data) {
							alert("cancle")
						},
						onUploadSuccess : function(file, data, response) {
							var flag = JSON.parse(data);
							debugger
							$("#pic").val(flag.url);
							$('#showPic').attr('src',flag.url);
						},
						onUploadError : function(file, errorCode, errorMsg,
								errorString) {
							alert('The file ' + file.name
									+ ' could not be uploaded: ' + errorString);
						}
					});
</script>
<div>
	<form id="myUserForm" method="post">
		<div style="float: left;">
			<div style="margin-bottom: 7px;">
				<input name="userId" hidden="true" /> <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
				<input name="userName" class="easyui-validatebox" required="true"
					style="width: 200px; height: 30px;"> <label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
				<span class="radioSpan"> <input type="radio" name="sex"
					value="1">男</input>&nbsp;&nbsp;&nbsp; <input type="radio"
					name="sex" value="0">女</input>
				</span>
			</div>
			<div style="margin-bottom: 7px;">
				<label>电子邮箱</label> <input name="email" class="easyui-validatebox"
					data-options="required:true,validType:'email'"
					style="width: 200px; height: 30px;"> <label>移动电话</label> <input
					name="mobile" style="width: 200px; height: 30px;"
					data-options="required:true,validType:'mobile'">
			</div>
			<div style="margin-bottom: 7px;">
				<label>所属学院</label> <input id="organId" class="easyui-combobox"
					name="organId" style="width: 200px; height: 30px;"
					data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do'">
				<label>所属院系</label>
				<input id="dept" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do'">
			</div>
			<div style="margin-bottom: 7px;">
				<label>生日日期</label> <input type="text" class="easyui-datebox"
					style="width: 200px; height: 30px;" name="birhtday">
				<label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址</label> <input
					name="address" class="easyui-validatebox"
					style="width: 355px; height: 30px;">
			</div>
			<div style="margin-bottom: 7px;" align="left">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
					onclick="saveMyUser()" style="margin-left: 160px; width: 100px;">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
					onclick="resetPass()" style="width: 100px;">修改密码</a>
			</div>
		</div>
		<div style="float: left;">
			<img src="" id="showPic" style="width: 100px;height: 100px;">	
			<input class="easyui-filebox" id ="pic" name="file2">		
		</div>
	</form>
</div>

<div id="dlg4MyUser" class="easyui-dialog"
	style="width: 390px; height: 230px; padding: 10px 20px" closed="true"
	buttons="#dlg-buttons-myUser">
	<form id="fm4MyUser" method="post">
		<div style="margin-bottom: 7px;">
			<input name="userId" hidden="true" /> <label>原&nbsp;密&nbsp;码&nbsp;&nbsp;：</label>
			<input type="password" name="oldPassword" class="easyui-validatebox"
				required="true" style="width: 200px; height: 30px;">
		</div>
		<div style="margin-bottom: 7px;">
			<label>新&nbsp;密&nbsp;码&nbsp;&nbsp;：</label> <input type="password"
				name="newPassword" class="easyui-validatebox"
				style="width: 200px; height: 30px;" required="true"
				validType="length[4,32]" id="newPassword">
		</div>
		<div style="margin-bottom: 7px;">
			<label>确认密码：</label> <input type="password"
				class="easyui-validatebox" style="width: 200px; height: 30px;"
				name="newPassword4Check" required="true" id="newPassword4Check">
		</div>
		<div style="margin-bottom: 7px; margin-left: 30px; color: red;"
			id="errorMsg"></div>
	</form>
</div>
<div id="dlg-buttons-myUser">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
		onclick="savePass()">保存密码</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:$('#dlg4MyUser').dialog('close')">取消</a>
</div>