<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1> Profile </h1>

                <p>
                    Name: <c:out value="${sessionScope.user.firstName}"/> <c:out value="${sessionScope.user.lastName}"/>
                    <a href="/edit_name" class="btn btn-secondary btn-sm">Edit name</a>
                </p>
                <p>
                    Age: <c:out value="${sessionScope.user.age}"/>
                    <a href="/edit_age" class="btn btn-secondary btn-sm">Edit age</a>
                </p>
                <p>
                    Email: <c:out value="${sessionScope.user.email}"/>
                    <a href="/edit_email" class="btn btn-secondary btn-sm">Edit email</a>
                </p>
                <p>
                    Role: <c:out value="${sessionScope.user.roleDto.toString()}"/>
                </p>
                <a href="/edit_password" class="btn btn-secondary btn-sm">Change password</a>

        </div>
    </body>
</html>