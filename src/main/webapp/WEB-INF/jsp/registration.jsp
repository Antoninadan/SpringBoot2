<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<h2><c:out value="${message}" default="Please, input your credentials!"/></h2>
<br>
<a href="/">GO BACK</a>
<br>
<form action="/user" method="put">
    <input type="text" size="30" name="login" required/>
    <br>
    <input type="password" size="30" name="pass" required/>
    <br>
    <input type="text" size="30" name="firstName" />
    <br>
    <input type="text" size="30" name="lastName" />
    <br>
    <input type="submit" value="REGISTER"/>
</form>
</body>
</html>
