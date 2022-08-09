<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <table class="table table-bordered border-primary my-2">
                <tr>
                    <th>Field</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td>ID</td>
                    <td>${order.id}</td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td>${order.user.firstName}</td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td>${order.user.lastName}</td>
                <tr>
                <tr>
                    <td>Status</td>
                    <td>${order.status.toString().toLowerCase()}</td>
                </tr>
                <tr>
                    <td>Total cost</td>
                    <td>${order.totalCost}</td>
                </tr>
                <tr>
                    <c:forEach items="${order.details}" var="info">
                        <td>Course: <a href="controller?command=book&id=${info.course.id}">${info.course.name}</a></td>
                        <td>${info.course.cost}</td>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </body>
</html>
