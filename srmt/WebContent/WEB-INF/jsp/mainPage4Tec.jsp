<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="./image/infomation.ico" type="image/x-icon" />
<meta charset="utf-8" />
<title>高校教师科研信息管理系统</title>
<link href="./css/bootstrap.min.css" rel="stylesheet" />
<link href="./css/themes/metro-blue/easyui.css" rel="stylesheet" />
<link href="./css/icon.css" rel="stylesheet" />
<link href="./uploadify/uploadify.css" rel="stylesheet" />
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="./js/json2.js"></script>
<script type="text/javascript" src="./uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="./js/jquery.fileDownload.js"></script>
<style>
article, aside, figure, footer, header, hgroup, menu, nav, section {
	display: block;
}

.west {
	width: 200px;
	padding: 10px;
}

.north {
	height: 100px;
}
</style>
<script type="text/javascript">
	$(function() {
		//动态菜单数据
		var tree3Data = [ {
			text : "修改个人信息",
			attributes : {
				url : "userMng/enterMyUser.do"
			},
			iconCls : "icon-myuser"
		} ];
		
		var tree2Data = [{
			text : "科研信息管理",
			attributes : {
				url : "research/enterResearchMng.do"
			},
			iconCls : "icon-teacher_info"
		},{
			text : "论文管理",
			attributes : {
				url : "research/enterThesisMng.do"
			},
			iconCls : "icon-thesis"
		}, {
			text : "项目管理",
			attributes : {
				url : "research/enterProjectMng.do"
			},
			iconCls : "icon-project"
		},{
			text : "奖励管理",
			attributes : {
				url : "research/enterRewardMng.do"
			},
			iconCls : "icon-reward"
		} ,{
			text : "专利管理",
			attributes : {
				url : "research/enterPatentMng.do"
			},
			iconCls : "icon-patent"
		}];
		
		
		var tree4Data = [ {
			text : "我的科研信息统计",
			attributes : {
				url : "research/enterWorkload4Tec.do"
			},
			iconCls : "icon-research_tec"
		} ];

		//实例化树形菜单
		$("#tree2").tree({
			data : tree2Data,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});

		//实例化树形菜单
		$("#tree3").tree({
			data : tree3Data,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});
		
		//实例化树形菜单
		$("#tree5").tree({
			data : tree4Data,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});

		//在右边center区域打开菜单，新增tab
		function Open(text, url) {
			if ($("#tabs").tabs('exists', text)) {
				$('#tabs').tabs('select', text);
			} else {
				$('#tabs').tabs('add', {
					title : text,
					closable : true,
					href : url
				});
			}
		}

		//绑定tabs的右键菜单
		$("#tabs").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				$('#tabsMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		//实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				CloseTab(this, item.name);
			}
		});

		//几个关闭事件的实现
		function CloseTab(menu, type) {
			var curTabTitle = $(menu).data("tabTitle");
			var tabs = $("#tabs");

			if (type === "close") {
				tabs.tabs("close", curTabTitle);
				return;
			}

			var allTabs = tabs.tabs("tabs");
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel("options");
				if (opt.closable && opt.title != curTabTitle
						&& type === "Other") {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === "All") {
					closeTabsTitle.push(opt.title);
				}
			});

			for (var i = 0; i < closeTabsTitle.length; i++) {
				tabs.tabs("close", closeTabsTitle[i]);
			}
		}
	});

	function loginOut(){
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "loginOut.do",
			async : true,
			success : function(data) {
				if(data.msg){
					var  jsp = "enterLoginPage.do";
	        		window.location.href = jsp;
				}
			},
		})
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" class="north" border="true" style="height: 60px;background-image:url(./image/head_pic.jpg); background-repeat:no-repeat;">
		<div align="right">
			<form>
				<label>欢迎你：</label> <label>${username}</label>&nbsp;&nbsp;<a
					href="javascript:void(0)" onclick="loginOut()">退出</a>
			</form>
		</div>
	</div>
	<div region="center" title="功能">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页"></div>
		</div>
	</div>
	<div region="west" class="west" title="菜单">
		<div id="RightAccordion" class="easyui-accordion" border="false"
			data-options="multiple:true">
			<div title="科研信息管理" data-options="iconCls:'icon-research'"
				style="padding: 10px;">
				<ul id="tree2"></ul>
			</div>
			<div title="科研信息统计" data-options="iconCls:'icon-mobile_statistics'"
				style="padding: 10px;">
				<ul id="tree5"></ul>
			</div>
			<div title="个人信息管理" data-options="iconCls:'icon-people_information'"
				style="overflow: auto; padding: 10px;">
				<ul id="tree3"></ul>
			</div>
		</div>
	</div>

	<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>
</body>
</html>