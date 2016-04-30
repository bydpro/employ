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
	<p>论文</p>
	<c:forEach var="message" items="${thesisList}">
		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

		<tr>
			<td>${message.thesisName}</td>
			<td>${message.workload}</td>
		</tr>
		<br>
	</c:forEach>
	<p>项目</p>
	<c:forEach var="project" items="${projectList}">
		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

		<tr>
			<td>${project.projectName}</td>
			<td>${project.workload}</td>
		</tr>
		<br>
	</c:forEach>

	<p>专利</p>
	<c:forEach var="patent" items="${patentList}">
		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

		<tr>
			<td>${patent.patentName}</td>
			<td>${patent.workload}</td>
		</tr>
		<br>
	</c:forEach>

	<p>奖励</p>
	<c:forEach var="reward" items="${rewardList}">
		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

		<tr>
			<td>${reward.rewardName}</td>
			<td>${reward.workload}</td>
		</tr>
		<br>
	</c:forEach>
</body>