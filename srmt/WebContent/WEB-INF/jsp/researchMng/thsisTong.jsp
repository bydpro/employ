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
	
	function clearForm() {
		$('#queryThesisTongForm').form('clear');
	}

	function doSearch() {
		debugger
		getData();
	}

	$(function() {
		getData();
		
	})
	

	function getData() {
		$.post('research/queryThesisTongList.do?' + Math.random(), $('#queryThesisTongForm').serializeObject(), function(data) {
			$('#thesisTongDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
			$("#size").html(data.length);
		});
	}

	function pagerFilter(data) {
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
		<label for="thesisName">教师姓名:</label> <input class="easyui-textbox"
			type="text" name="userName" style="width: 200px; height: 30px;" />
		<label for="thesisName">论文名称:</label> <input class="easyui-textbox"
			type="text" name="thesisName" style="width: 200px; height: 30px;" />
			
			<label >论文类别：</label>
			<input id="thesisType" class="easyui-combobox" name="thesisType" style="width: 200px; height: 30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryThesisType.do'">
    		</div>	
    <div style="margin-bottom: 7px;">
		<input class="easyui-linkbutton" type="button" value="检索"
			style="width: 198px; height: 30px; margin-left: 843px"
			onclick="doSearch()"> 
	</div>

</form>
<div style="color: blue;"><font size="5">共检索到</font> <font id="size" size="5"></font><font size="5">条记录</font></div>
<table id="thesisTongDg" title="论文信息统计列表" 
	style="width: 1050px; height: 70%;" toolbar="#thesisDgBar" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:20">
	<thead>
		<tr>
			<th field="USERNUM" width="40px" align="center">教师编号</th>
			<th field="USERNAME" width="40px">姓名</th>
			<th field="THESISNAME" width="60px">论文名称</th>
			<th field="THESISTYPE" width="40px" align="center">论文类型</th>
			<th field="THESISAUTHOR" width="50px">全部作者</th>
			<th field="THESISRECORD" width="60px">发表期刊</th>
			<th field="THESISPERIODICAL" width="80px">论文收录情况</th>
			<th field="workload" width="25px" align="center">论文科研分</th>
			<th field="USERID" width="50" hidden="true">USERID</th>
		</tr>
	</thead>
</table>