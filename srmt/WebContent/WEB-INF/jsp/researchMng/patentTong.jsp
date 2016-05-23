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

	function formatValue(val, row) {
		if (val == 1) {
			return '是';
		} else {
			return '否';
		}
	}

	function getData() {
		$.post('research/queryPatentTongList.do?' + Math.random(), $('#queryPatentTongForm').serializeObject(), function(data) {
			$('#patentTongDg').datagrid({loadFilter : pagerFilter}).datagrid('loadData', data);
			$("#size4patent").html(data.length);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#patentTongDg');
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
	
	function doSearch() {
		getData();
	}

	$(function() {
		getData();
	});
	
</script>

<form id="queryPatentTongForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="projectName">教师编号:</label> <input class="easyui-textbox"
			type="text" name="userNum" style="width: 180px; height: 30px;" />
		<label for="projectName">教师姓名:</label> <input class="easyui-textbox"
			type="text" name="userName" style="width: 180px; height: 30px;" />
		<label for="patentName">知识产权名称:</label> <input class="easyui-textbox"
			type="text" name="patentName" style="width: 180px; height: 30px;" />
		 <label>知识产权类型:</label>
 		<input  class="easyui-combobox" name="patentType" style="width:180px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentType.do'">
	</div>
	<div style="margin-bottom: 7px;">
		<label>专利人权类别:</label>
 		<input id="patentPeople" class="easyui-combobox" name="patentPeople" style="width:155px;height:30px;"
    			data-options="valueField:'DICTVALUE',textField:'DICTNAME',url:'research/queryPatentPeople.do'">
		<label>授权日期:</label>
		<input name="patentDate" style="width:180px;height:30px;" class="easyui-datebox">
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
		<input class="easyui-linkbutton" type="button" value="检索"
			style="width: 98px; height: 30px; margin-left: 5px"
			onclick="doSearch()"> 
	</div>

</form>
<div style="color: blue;"><font size="5">共检索到</font> <font id="size4patent" size="5"></font><font size="5">条记录</font></div>
<table id="patentTongDg" title="专利信息统计列表" 
	style="width: 1050px; height: 72%;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="USERNUM" width="50">教师编号</th>
			<th field="USERNAME" width="50">姓名</th>
			<th field="PATENTNAME" width="50">知识产权名称</th>
			<th field="PATENTTYPE" width="50">知识产权类型</th>
			<th field="PATENTPEOPLE" width="50">专利人权类别</th>
			<th field="PATENTCREATER" width="50">发明人</th>
			<th field="PATENTFIRST" width="50" formatter="formatValue" align="center">是否首位</th>
			<th field="PATENTISTRANSFER" width="50" formatter="formatValue" align="center">是 否转让</th>
			<th field="PATENTDATE" width="50" align="center">授权日期 </th>
			<th field="workload" width="50" align="center">科研分 </th>
			<th field="USERID" width="50" hidden="true">USERID</th>
			<th field="RID" width="50" hidden="true">RID</th>
		</tr>
	</thead>
</table>