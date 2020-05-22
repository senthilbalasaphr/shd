<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<title>Position Report</title>
</head>
<body>
<form:form id="PositionReportForm" method="post" action="login"
		modelAttribute="reports">
		<h1>Position Report</h1>


		<br>

		<table border="1">
			<tr>
	 
				<td>
				<form:label path="company">Company</form:label>
				<form:input id="company" name="company" path="company" />
				</td>

			</tr>

		</table>
	</form:form>
</body>
</html>
