<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-4">
            <h1> Login </h1>
            <p><c:out value="${requestScope.message}"/></p>
            <form method="post" action="controller">
                <p>
                <input name="command" type="hidden" value="login"/>
                <label for="email-input">Email:</label>
                <input id="email-input" name="email" type="email"/>
                </p>
                <p>
                <label for="password-input">Password:</label>
                <input id="password-input" name="password" type="password" minlength="4"/>
                </p>
                <p>
                <input type="submit" value="Login"/>
                </p>
            </form>
        </div>
    </body>
</html>