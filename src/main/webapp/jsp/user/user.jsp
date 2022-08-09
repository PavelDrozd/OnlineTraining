<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>User</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <p><c:out value="${requestScope.message}"/></p>

            <table class="table table-bordered border-primary my-2">
                <tr><th>Field</th><th>Value</th></tr>
                <tr><td>ID</td><td><c:out value="${user.id}"/></td><tr>
                <tr><td>First name</td><td><c:out value="${user.firstName}"/></td><tr>
                <tr><td>Last name</td><td><c:out value="${user.lastName}"/></td><tr>
                <tr><td>Age</td><td><c:out value="${user.age}"/></td><tr>
                <tr><td>Email</td><td><c:out value="${user.email}"/></td><tr>
            </table>
        </div>
    </body>
</html>
