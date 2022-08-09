<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Users</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <table class="table table-bordered border-primary my-2">
                <tr>
                    <th>#</th>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Age</th>
                    <th>Email</th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td><a href="controller?command=user&id=${user.id}">${user.email}</a></td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../paging/pagination.jsp"/>
        </div>
    </body>
</html>
