<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="utf-8" />
<script type="text/javascript">
	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "research/getCurrentThesisWorkload4Tec.do",
			async : true,
			success : function(data) {

			},
		});
	});
</script>
<body>
	<table class="table table-bordered table-hover table-striped"
		style="margin-bottom: 0px;">
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>我的科研信息统计</h2>
		</caption>
		<thead>
			<tr>
				<td align="center">论文数量</td>
				<td align="center">项目数量</td>
				<td align="center">专利数量</td>
				<td align="center">奖励数量</td>
				<td align="center">科研信息总分</td>
			</tr>
			<tr>
				<td align="center" class="warning">${sumMap.thesisSize}</td>
				<td align="center" class="warning">${sumMap.projectSize}</td>
				<td align="center" class="warning">${sumMap.rewardSize}</td>
				<td align="center" class="warning">${sumMap.patentSize}</td>
				<td align="center" class="warning">${sumMap.sumWorkload}</td>
			</tr>
		</thead>
	</table>

	<table class="table table-bordered table-hover table-striped"
		style="margin-bottom: 0px;">
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>论文科研统计</h2>
		</caption>
		<%
			int i = 1;
		%>
		<thead>
			<tr>

				<td align="center">序号</td>
				<td align="center">论文名称</td>
				<td align="center">论文所得科研分</td>
			</tr>
		</thead>
		<c:forEach var="message" items="${thesisList}">
			<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

			<tr>
				<%
					if (i % 2 == 0) {
				%>
				<td align="center" class="success"><%=i%></td>
				<%
					i = i + 1;
				%>
				<td align="center" class="success">${message.thesisName}</td>
				<td align="center" class="success">${message.workload}</td>

				<%
					} else {
				%>
				<td align="center"><%=i%></td>
				<%
					i = i + 1;
				%>
				<td align="center">${message.thesisName}</td>
				<td align="center">${message.workload}</td>
				<%
					}
				%>
			</tr>
		</c:forEach>
	</table>

	<table class="table table-bordered table-hover table-striped "
		style="margin-bottom: 0px;">
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>项目科研统计</h2>
		</caption>
		<%
			int b = 1;
		%>
		<thead>
			<tr>

				<td align="center">序号</td>
				<td align="center">项目名称</td>
				<td align="center">项目所得科研分</td>
			</tr>
		</thead>
		<c:forEach var="project" items="${projectList}">
			<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

			<tr>
				<%
					if (b % 2 == 0) {
				%>
				<td align="center" class="success"><%=b%></td>
				<%
					b = b + 1;
				%>
				<td class="success">${project.projectName}</td>
				<td class="success">${project.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=b%></td>
				<%
					b = b + 1;
				%>
				<td>${project.projectName}</td>
				<td>${project.workload}</td>
				<%
					}
				%>
			</tr>
		</c:forEach>
	</table>

	<table class="table table-bordered table-hover table-striped "
		style="margin-bottom: 0px;">
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>专利科研统计</h2>
		</caption>
		<%
			int c = 1;
		%>
		<thead>
			<tr>

				<td align="center">序号</td>
				<td align="center">专利名称</td>
				<td align="center">专利所得科研分</td>
			</tr>
		</thead>
		<c:forEach var="patent" items="${patentList}">
			<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

			<tr>
				<%
					if (c % 2 == 0) {
				%>
				<td align="center" class="success"><%=c%></td>
				<%
					c = c + 1;
				%>
				<td class="success">${patent.patentName}</td>
				<td class="success">${patent.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=c%></td>
				<%
					c = c + 1;
				%>
				<td>${patent.patentName}</td>
				<td>${patent.workload}</td>
				<%
					}
				%>
			</tr>
		</c:forEach>
	</table>

	<table class="table table-bordered table-hover table-striped"
		style="margin-bottom: 0px;">
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>奖励科研统计</h2>
		</caption>
		<%
			int d = 1;
		%>
		<thead>
			<tr>

				<td align="center">序号</td>
				<td align="center">奖励名称</td>
				<td align="center">奖励所得科研分</td>
			</tr>
		</thead>
		<c:forEach var="reward" items="${rewardList}">
			<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

			<tr>
				<%
					if (d % 2 == 0) {
				%>
				<td align="center" class="success"><%=d%></td>
				<%
					d = d + 1;
				%>
				<td class="success">${reward.rewardName}</td>
				<td class="success">${reward.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=d%></td>
				<%
					d = d + 1;
				%>
				<td>${reward.rewardName}</td>
				<td>${reward.workload}</td>
				<%
					}
				%>
			</tr>
		</c:forEach>
	</table>
</body>