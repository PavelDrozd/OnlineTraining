<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register new user</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1> <spring:message code="msg.nav.register"/> </h1>
            <p><c:out value="${requestScope.message}"/></p>
                <form method="post" action="/register_new_user">
                    <p>
                    <input name="command" type="hidden" value="create_user"/>
                    <label for="firstName-input"><spring:message code="msg.user.personalinfo.firstname"/>: </label>
                    <input id="firstName-input" name="firstName" type="text"/>
                    </p>
                    <p>
                    <label for="lasName-input"><spring:message code="msg.user.personalinfo.lastname"/>: </label>
                    <input id="lastName-input" name="lastName" type="text"/>
                    </p>
                    <p>
                    <label for="email-input"><spring:message code="msg.user.personalinfo.email"/>: </label>
                    <input id="email-input" name="email" type="email"/>
                    </p>
                    <p>
                    <label for="password-input"><spring:message code="msg.user.password"/>: </label>
                    <input id="password-input" name="password" type="password" minlength="4"/>
                    </p>
                    <p>
                    <input type="submit" value=<fmt:message code="msg.nav.register"/>
                    </p>
                </form>
        </div>
    </body>
</html>