<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Carts</title>
</head>
<body>
	<h2>Carts:</h2>

	<!-- Table of carts  -->
	<table>
		<tr>
			<th>id</th>
			<th>status</th>
		</tr>
		<c:forEach items="${cartCollection}" var="cart">
			<form action="/order/items-by-cart" method="get">
			<tr>
				<td><c:out value="${cart.id}" /></td>
				<td><c:out value="${cart.status}" /></td>
				<td><input hidden="true" name="userId" value="${user.id}">
				</td>
				<td><input hidden="true" name="cartId" value="${cart.id}">
				</td>
				<td><input type="submit" value="open"></td>
			</tr>
			</form>
		</c:forEach>
	</table>
	<br>
	<br>
	<br>

	<!-- Back to cabinet -->
	<form action="/user/cabinet" method="get">
		<input hidden="true" name="userId" value="${user.id}">
		<input type="submit" value="Back to cabinet">
	</form>

</body>
</html>