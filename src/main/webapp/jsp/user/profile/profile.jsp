<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
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
                    <a href="controller?command=edit_name_form" class="btn btn-secondary btn-sm">Edit name</a>
                </p>
                <p>
                    Age: <c:out value="${sessionScope.user.age}"/>
                    <a href="controller?command=edit_age_form" class="btn btn-secondary btn-sm">Edit age</a>
                </p>
                <p>
                    Email: <c:out value="${sessionScope.user.email}"/>
                    <a href="controller?command=edit_email_form" class="btn btn-secondary btn-sm">Edit email</a>
                </p>
                <p>
                    Role: <c:out value="${sessionScope.user.roleDto.toString()}"/>
                </p>
                <a href="controller?command=edit_password_form" class="btn btn-secondary btn-sm">Change password</a>

        </div>
    </body>
</html>