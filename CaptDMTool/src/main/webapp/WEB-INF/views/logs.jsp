<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>
window.setInterval("reloadIFrame();", 1000);

function reloadIFrame() {
 document.frames["logFrame"].location.reload();
}
</script>
</head>
<body>
<form:form method="GET" 
		name="logsForm"
		action="/showLogs">
<%-- <input type="text" value="${sessionScope.data}" /> --%>
<p>${logs}</p>
</form:form>
</body>
</html>