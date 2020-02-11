<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>

<h2>
    Cart, id =
    <c:out value="${cart.id} ${cart.status}"/>
</h2>

<!-- Button "To be closed" -->
<c:if test="${cart.status == 'OPEN'}">
    <form action="/cart/do-cart-to-be-closed" method="post">
        <input hidden="true" name="userId" value="${user.id}">
        <input hidden="true" name="cartId" value="${cart.id}">
        <input type="submit" value="To be closed">
    </form>
</c:if>

<!-- Button "Closed" -->
<c:if test="${cart.status == 'TO_BE_CLOSED'}">
    <form action="/cart/do-cart-closed" method="post">
        <input hidden="true" name="userId" value="${user.id}">
        <input hidden="true" name="cartId" value="${cart.id}">
        <input type="submit" value="Close">
    </form>
</c:if>
<br>
<br>

<!-- Table of items in cart -->
<table>
    <h3> ITEMS IN CART: </h3>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>price</th>
        <th>amount</th>
    </tr>
    <c:forEach items="${orderDTOCollection}" var="orderDTO">
        <tr>
            <td><c:out value="${orderDTO.itemId}"/></td>
            <td><c:out value="${orderDTO.itemName}"/></td>
            <td><c:out value="${orderDTO.itemPrice}"/></td>
            <td><c:out value="${orderDTO.amount}"/></td>
        </tr>
    </c:forEach>
</table>
<br>
<br>

<!-- All carts -->
<form action="/cart/all-by-user" method="get">
    <input type="text" name="action" value="all-carts" hidden>
    <input hidden="true" name="userId" value="${user.id}">
    <input type="submit" value="All carts">
</form>
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
