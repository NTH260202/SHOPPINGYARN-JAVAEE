<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2/20/2022
  Time: 1:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Yarn Store</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}"/>

        <c:if test="${not empty user}">
            <span style="color: red; "> Welcome, ${user.firstname} </span><br/>
            <a href="logout">Log Out</a>
        </c:if>

        <h1>Yarn Store</h1>
            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach varStatus="index" var="item" items="${requestScope.VIEW_RESULT}">
                    <form action="addItemToCart">
                        <tr>
                            <td>${index.count}</td>
                            <td>${item.name}</td>
                            <td>${item.price}</td>
                            <td>${item.inStock}</td>
                            <td><button type="submit" name="selectedItem" value="${item.id}">Add Item</button></td>
                        </tr>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        <a href="cartPage">View Your Cart</a><br/>
    </body>
</html>
