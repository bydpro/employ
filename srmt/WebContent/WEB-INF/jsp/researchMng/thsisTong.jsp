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
	

	function doSearch4thesisTong() {
		getData4thesisTong();
	}

	$(function() {
		getData4thesisTong();
		$('#organ4thesistong')
		.combobox(
				{onSelect : function() {
						$('#dept4thesistong').combobox('clear');
						$('#username4thesistong').combobox('clear');
						var url = 'organMng/queryDept.do?organId='
								+ $('#organ4thesistong').combobox('getValue');
						$('#dept4thesistong').combobox('reload', url);
						$('#username4thesistong').combobox('reload', 'userMng/queryUser4sel.do?organId='
								+ $('#organ4thesistong').combobox('getValue')+'&deptId=');
					}
				});
		
		$('#dept4thesistong')
		.combobox(
				{onSelect : function() {
						$('#username4thesistong').combobox('clear');
						$('#username4thesistong').combobox('reload', 'userMng/queryUser4sel.do?deptId='
								+ $('#dept4thesistong').combobox('getValue')+'&organId=');
					}
				});
		
		
	})
	

	function getData4thesisTong() {
		$.post('research/queryThesisTongList.do?' + Math.random(), $('#queryThesisTongForm').serializeObject(), function(data) {
			$('#thesisTongDg').datagrid({loadFilter : pagerFilter4thesisTong}).datagrid('loadData', data);
			$("#size").html(data.length);
		});
	}

	function pagerFilter4thesisTong(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#thesisTongDg');
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
<form id="queryThesisTongForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="thesisName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 200px; height: 30px;" />
								<label for="organ4thesis">所属学院:</label>
		<input id="organ4thesistong" class="easyui-combobox" name="organId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryOrgan4dept.do',editable:false">
     	<label>所属系部:</label>
		<input id="dept4thesistong" class="easyui-combobox" name="deptId" style="width:200px;height:30px;"
    			data-options="valueField:'ORGANID',textField:'ORGANNAME',url:'organMng/queryDept.do',editable:false">
		<label for="username">教师姓名:</label>
		<input class="easyui-combobox" type="text" name="userName"  style="width:200px;height:30px;"
			data-options="valueField:'str',textField:'username',url:'userMng/queryUser4sel.do'" id="username4thesistong"/>
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
		<input class="easyui-linkbutton" type="button" value="检索"
			style="width: 198px; height: 30px; margin-left: 842px"
			onclick="doSearch4thesisTong()"> 
	</div>

</form>
<div style="color: blue;"><font size="5">共检索到</font> <font id="size" size="5"></font><font size="5">条记录</font></div>
<table id="thesisTongDg" title="论文信息统计列表" 
	style="width: 1130px; height: 67%;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNUM" width="45px" align="center">教师编号</th>
			<th field="USERNAME" width="30px">姓名</th>
				<th field="THESISNAME" width="60px">论文名称</th>
			<th field="THESISTYPE" width="30px" align="center">论文类型</th>
			<th field="THESISAUTHOR" width="50px">全部作者</th>
			<th field="THESISRECORD" width="60px">发表期刊</th>
			<th field="THESISPAGE" width="50px">发表卷、期、页码</th>
			<th field="THESISDATE" width="40px">发表日期</th>
			<th field="THESISPERIODICAL" width="80px">论文收录情况</th>
			<th field="workload" width="25px" align="center">论文科研分</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
		</tr>
	</thead>
</table>