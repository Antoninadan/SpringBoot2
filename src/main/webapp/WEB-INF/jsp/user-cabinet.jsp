<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Logined Page</title>
</head>
<body>
<h2>Hello, ${user.firstName} ${user.lastName}!!!</h2>

<!-- All carts -->
<form action="/cart/all-by-user" method="get">
    <input type="text" name="action" value="all-carts" hidden> <input
        hidden="true" name="userId" value="${user.id}"> <input
        type="submit" value="All carts">
</form>
<br>
<br>

<!-- Table of available items  -->
<table>
    Available items:
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>PRICE</th>
    </tr>
    <c:forEach items="${itemCollection}" var="item">
        <form action="/item/add-item-in-cart" method="post">
            <tr>
                <td><c:out value="${item.id}" /></td>
                <td><c:out value="${item.name}" /></td>
                <td><c:out value="${item.price}" /></td>
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
        <br> Your item was added to cart with id=:
        <c:out value="${cart.id}" />
    </h3>

    <form action="item" method="post">
        <input type="text" name="action" value="open-current-cart" hidden>
        <input name="userId" value="${user.id}" hidden> <input
            type="submit" value="Open cart">
    </form>
</c:if>


</body>
</html>