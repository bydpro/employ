<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<c:forEach var="message" items="${thesisList}">
		<%--用EL表达式调用list对象的属性循环输出对象的各个属性值--%>

		<tr>
			<td>${message.thesisName}</td>
			<td>${message.messageSendTime}</td>
			<td>${message.messageValidTime}</td>
			<td>${message.messageValidTime}</td>
			<td>${message.messageContent}</td>
		</tr>

	</c:forEach>
</body>