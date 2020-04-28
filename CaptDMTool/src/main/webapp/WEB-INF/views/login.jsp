<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

<style>  
.error{color:red}  
</style>
</head>
<body>
	<c:if test="${not empty error}">
   		<p style="color:red">${error}</p>
	</c:if>
	<form:form id="loginForm" method="post" action="login"
		modelAttribute="loginBean">

		<form:label path="userName">User Name</form:label>
		<form:input id="username" name="userName" path="userName" />
		<form:errors path="userName" cssClass="error"/>
		<br>
		<form:label path="password">Password</form:label>
		<form:password id="password" name="password" path="password" />
		<form:errors path="password" cssClass="error"/>
		<br>
		<input type="submit" value="Submit" />
	</form:form>
</body>
</html>