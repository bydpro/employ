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
		$('#gasFf').form('clear');
	}

	function doSearch() {
		getData();
	}
	function getData() {
		$.post('oilStorage/queryMyGasRecord.do?' + Math.random(), $('#gasFf')
				.serializeObject(), function(data) {
			$('#gasDg').datagrid({
				loadFilter : pagerFilter
			}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#gasDg');
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

	$(function() {
		getData();

	})
	

	function formatPrice(val, row) {
		return "￥" + val;
	}
</script>
<form id="gasFf" method="post">
</form>
<table id="gasDg" title="我的加油记录列表" style="width: 1050px; height: 95%;"
	data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="username" width="60" align="center">加油人</th>
			<th field="gasusernum" width="60" align="center">卡号</th>
			<th field="gasid" width="50" hidden="true"></th>
			<th field="gastype" width="50" align="center">油品类型</th>
			<th field="gasprice" width="50"   formatter="formatPrice" dataType="float">油价</th>
			<th field="gasvolume" width="50" dataType="float">加油量</th>
			<th field="userMoney" width="50" formatter="formatPrice" dataType="float">消费额</th>
			<th field="gastime" width="60" align="center">加油时间</th>						
		</tr>
	</thead>
</table>