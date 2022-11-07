<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-4">
            <h1><spring:message code="msg.nav.login"/></h1>
            <p><c:out value="${requestScope.message}"/></p>
            <form method="post" action="/login_user">
                <p>
                <input name="command" type="hidden" value="login"/>
                <label for="email-input"><spring:message code="msg.user.login"/>:</label>
                <input id="email-input" name="login" type="text"/>
                </p>
                <p>
                <label for="password-input"><spring:message code="msg.user.password"/>:</label>
                <input id="password-input" name="password" type="password" minlength="4"/>
                </p>
                <p>
                <input type="submit" value=<spring:message code="msg.nav.login"/>
                </p>
            </form>
        </div>
    </body>
</html>