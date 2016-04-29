<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript" src="./js/highcharts.js"></script>
<script src="./js/exporting.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		var qi95 = '';
		var qi97 = '';
		var chaiGas = '';
		var categorie = '';
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "oilStorage/getOilSalInfo.do",
			data : {},
			async : true,
			success : function(data) {
				categorie = data.categories;
				var first = categorie[6];
				var second = categorie[5];
				var third = categorie[4];
				var fourth = categorie[3];
				var fifth = categorie[2];
				var sixth = categorie[1];
				qi95 = data.qi95;
				qi97 = data.qi97;
				chaiGas = data.chaiGas;
				$('#container').highcharts(
						{ //图表展示容器，与div的id保持一致
							chart : {
								type : 'line' //指定图表的类型，默认是折线图（line）
							},
							title : {
								text : '半年内的销售情况图' //指定图表标题
							},
							xAxis : {
								categories : [ first, second, third, fourth,
										fifth, sixth ]
							//指定x轴分组
							},
							yAxis : {
								title : {
									text : '单位/升' //指定y轴的标题
								}
							},
							series : [
									{ //指定数据列
										name : '汽油(95号)', //数据列名
										data : [ qi95[6], qi95[5], qi95[4],
												qi95[3], qi95[2], qi95[1] ]
									//数据
									},
									{
										name : '汽油(93号)',
										data : [ qi97[6], qi97[5], qi97[4],
												qi97[3], qi97[2], qi97[1] ]
									},
									{
										name : '柴油',
										data : [ chaiGas[6], chaiGas[5],
												chaiGas[4], chaiGas[3],
												chaiGas[2], chaiGas[1] ]
									} ]
						});
			},
		})
	
		
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "oilStorage/queryOilStorage4All.do",
			data : {},
			async : true,
			success : function(data) {
				$('#container4StorageAll')
				.highcharts(
						{
						    chart: {
					            plotBackgroundColor: null,
					            plotBorderWidth: null,
					            plotShadow: false
					        },
					        title: {
					            text: '仓库油量使用状况图'
					        },
					        tooltip: {
					    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					        },
					        plotOptions: {
					            pie: {
					                allowPointSelect: true,
					                cursor: 'pointer',
					                dataLabels: {
					                    enabled: true,
					                    color: '#000000',
					                    connectorColor: '#000000',
					                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
					                }
					            }
					        },
							series : [ {
								type : 'pie',
								name : '仓库油量使用情况',
								data : [ [ '柴油(已售)', data.useChai*100 ], [ '汽油95号(已售)', data.useQI95*100 ], {
									name : '汽油97号(已售)',
									y : data.useQI97*100,
									sliced : true,
									selected : true
								}, [ '柴油(剩余)', data.unChai*100 ], [ '汽油95号(剩余)', data.unQI95*100 ],
										[ '汽油97号(剩余)', data.unQI97*100 ] ]
							} ]
						});
				
				$('#container4Storage')
				.highcharts(
						{
							chart : {
								plotBackgroundColor : null,
								plotBorderWidth : null,
								plotShadow : false
							},
							title : {
								text : '仓库油量使用情况图'
							},
							tooltip : {
								
								formatter: function() {
										return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
										Highcharts.numberFormat(data.totalNum*this.percentage/100, 0, ',') +' 桶)';
										}
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									dataLabels : {
										enabled : true,
										color : '#000000',
										connectorColor : '#000000',
										format : '<b>{point.name}</b>: {point.percentage:.1f} %'
									}
								}
							},
							series : [ {
								type : 'pie',
								name : '仓库油量使用情况',
								data : [ [ '油量存储剩余比例', data.leaveTotalPre*100 ],{
									name : '油量存储使用比例',
									y : data.useTotalPre*100,
									sliced : true,
									selected : true
								} ]
							} ]
						});
			},
		})
		

	});
</script>
<div id="container" style="min-width: 300px; height: 300px"></div>
<div align="left">
	<div id="container4StorageAll" style="width: 57%; height: 400px;float:left"></div>
	<div id="container4Storage" style="width: 43%; height: 400px;float:left"></div>
</div>