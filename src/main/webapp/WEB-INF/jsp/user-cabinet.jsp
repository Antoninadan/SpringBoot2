<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Logined Page</title>
</head>
<body>
<h2>Hello, ${user.firstName} ${user.lastName}!!!</h2>
<br>
<br>

<!-- Table of available items  -->
<table>
    AVAILABLE ITEMS:
    <tr>
        <th>id</th>
        <th>name</th>
        <th>price</th>
    </tr>
    <c:forEach items="${itemCollection}" var="item">
        <form action="/item/add-item-in-cart" method="post">
            <tr>
                <td><c:out value="${item.id}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.price}"/></td>
                <td><input hidden="true" name="userId" value="${user.id}">
                </td>
                <td><input hidden="true" name="itemId" value="${item.id}">
                </td>
                <td><input type="submit" value="buy"></td>
            </tr>
        </form>
    </c:forEach>
</table>


<c:if test="${cart != null}">
    <h3>
        <br> Your item was added to CART with ID =
        <c:out value="${cart.id}"/>
    </h3>

    <form action="/order/items-by-cart" method="get">
        <input name="userId" value="${user.id}" hidden>
        <input name="cartId" value="${cart.id}" hidden>
        <input type="submit" value="Open cart">
    </form>
</c:if>
<br>
<br>

<!-- All carts -->
<form action="/cart/all-by-user" method="get">
    <input type="text" name="action" value="all-carts" hidden> <input
        hidden="true" name="userId" value="${user.id}"> <input
        type="submit" value="All carts">
</form>

</body>
</html>