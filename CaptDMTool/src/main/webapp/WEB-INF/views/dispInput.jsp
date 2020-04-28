<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<form:form>
		<h1>CAPT Data Migration Tool</h1>
		<P>Tool for all your Data Migration Needs...</P>

		<br>

		<table border="1">
			<tr>
				<td><b>Client</b></td>
				<td><b>TEMPLATEGRP</b></td>
				<td><b>TEMPLATE</b></td>
				<td><b>SEQNR</b></td>
				<td><b>FIELD_NAME</b></td>
				<td><b>FIELD_TYPE</b></td>
				<td><b>FIELD_LENGTH</b></td>
				<td><b>VALUE_MAPPING</b></td>
				<td><b>FUNCTION_ID</b></td>
				<td><b>FUNCTION_ROUTINE</b></td>
			</tr>
			<c:forEach items="${resultBean}" var="result" varStatus="tagStatus">
				<tr>
					<td><c:out value="${result.client}" /></td>
					<td><c:out value="${result.templateGrp}" /></td>
					<td><c:out value="${result.template}" /></td>
					<td><c:out value="${result.seqNo}" /></td>
					<td><c:out value="${result.fieldName}" /></td>
					<td><c:out value="${result.fieldType}" /></td>
					<td><c:out value="${result.fieldLength}" /></td>
					<td><c:out value="${result.valueMapping}" /></td>
					<td><c:out value="${result.functionId}" /></td>
					<td><c:out value="${result.functionRoutine}" /></td>
				</tr>
			</c:forEach>
		</table>
	</form:form>
</body>
</html>
