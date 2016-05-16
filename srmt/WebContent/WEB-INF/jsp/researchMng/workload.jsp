<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="utf-8" />
<body>
	<table class="table table-bordered table-hover table-striped"
		style="margin-bottom: 0px;">
		<thead>
		<caption style="padding-top: 0px; padding-bottom: 0px;">
			<h2>教师信息</h2>
		</caption>
			<tr>
				<td align="center">教师编号</td>
				<td align="center">姓名</td>
				<td align="center">电子邮箱</td>
				<td align="center">手机号码</td>
			</tr>
			<tr>
				<td align="center" class="warning">${userInfo.userNum}</td>
				<td align="center" class="warning">${userInfo.userName}</td>
				<td align="center" class="warning">${userInfo.email}</td>
				<td align="center" class="warning">${userInfo.mobile}</td>
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
				<td align="center">论文类型</td>
				<td align="center">论文收录情况</td>
				<td align="center">全部作者</td>
				<td align="center">发表期刊</td>
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
				<td align="center" class="success">${message.THESISNAME}</td>
				<td align="center" class="success">${message.THESISTYPE}</td>
				<td align="center" class="success">${message.THESISPERIODICAL}</td>
				<td align="center" class="success">${message.THESISAUTHOR}</td>
				<td align="center" class="success">${message.THESISRECORD}</td>
				<td align="center" class="success">${message.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=i%></td>
				<%
					i = i + 1;
				%>
				<td align="center" >${message.THESISNAME}</td>
				<td align="center" >${message.THESISTYPE}</td>
				<td align="center" >${message.THESISPERIODICAL}</td>
				<td align="center" >${message.THESISAUTHOR}</td>				
				<td align="center" >${message.THESISRECORD}</td>
				<td align="center" >${message.workload}</td>
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
				<td align="center">项目类型</td>
				<td align="center">开始日期</td>
				<td align="center">开始日期</td>
				<td align="center">项目经费</td>
				<td align="center">项目来源</td>
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
				<td class="success">${project.PROJECTNAME}</td>
				<td class="success">${project.PROJECTTYPE}</td>
				<td class="success">${project.STARTTIME}</td>
				<td class="success">${project.ENDTIME}</td>
				<td class="success">${project.PROJECTFUND}</td>
				<td class="success">${project.PROJECTSOURCE}</td>
				<td class="success">${project.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=b%></td>
				<%
					b = b + 1;
				%>
				<td >${project.PROJECTNAME}</td>
				<td >${project.PROJECTTYPE}</td>
				<td >${project.STARTTIME}</td>
				<td >${project.ENDTIME}</td>
				<td >${project.PROJECTFUND}</td>
				<td >${project.PROJECTSOURCE}</td>
				<td >${project.workload}</td>
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
				<td align="center">知识产权名称</td>
				<td align="center">知识产权类型</td>
				<td align="center">专利人权类别</td>
				<td align="center">是否首位</td>
				<td align="center">是 否转让</td>
				<td align="center">授权日期</td>
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
				<td class="success">${patent.PATENTNAME}</td>
				<td class="success">${patent.PATENTTYPE}</td>
				<td class="success">${patent.PATENTPEOPLE}</td>
				<td class="success">${patent.PATENTFIRSTSTR}</td>
				<td class="success">${patent.PATENTISTRANSFER}</td>
				<td class="success">${patent.PATENTDATE}</td>
				<td class="success">${patent.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=c%></td>
				<%
					c = c + 1;
				%>
				<td >${patent.PATENTNAME}</td>
				<td >${patent.PATENTTYPE}</td>
				<td >${patent.PATENTPEOPLE}</td>
				<td >${patent.PATENTFIRSTSTR}</td>
				<td >${patent.PATENTISTRANSFERSTR}</td>
				<td >${patent.PATENTDATE}</td>
				<td >${patent.workload}</td>
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
				<td align="center">奖励名称</td>
				<td align="center">奖励所得科研分</td>
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
				<td class="success">${reward.REWARDNAME}</td>
				<td class="success">${reward.REWARDTYPE}</td>
				<td class="success">${reward.REWARDTIME}</td>
				<td class="success">${reward.REWARDORGAN}</td>
				<td class="success">${reward.REWARDUSER}</td>
				<td class="success">${reward.workload}</td>
				<%
					} else {
				%>
				<td align="center"><%=d%></td>
				<%
					d = d + 1;
				%>
				<td >${reward.REWARDNAME}</td>
				<td >${reward.REWARDTYPE}</td>
				<td >${reward.REWARDTIME}</td>
				<td >${reward.REWARDORGAN}</td>
				<td >${reward.REWARDUSER}</td>
				<td >${reward.workload}</td>
				<%
					}
				%>
			</tr>
		</c:forEach>
	</table>
</body>