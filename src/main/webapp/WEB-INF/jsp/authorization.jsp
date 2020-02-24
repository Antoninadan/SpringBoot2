<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<h2><c: out value = "${message}" default="Hello! This is landing page!"></c:out></h2>
<br>
<a href="/registration">REGISTER NEW USER</a>
<br>
<form action="/user/auth" method="post">
    <input type="text" size="30" name="login" required/>
    <br>
    <input type="password" size="30" name="pass" required/>
    <br>
    <input type="submit" value="LOGIN"/>
</form>
</body>
</html>
