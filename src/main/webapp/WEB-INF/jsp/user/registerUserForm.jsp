<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register new user</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1> <fmt:message key="msg.nav.register"/> </h1>
            <p><c:out value="${requestScope.message}"/></p>
                <form method="post" action="/register_new_user">
                    <p>
                    <input name="command" type="hidden" value="create_user"/>
                    <label for="firstName-input"><fmt:message key="msg.user.firstname"/>: </label>
                    <input id="firstName-input" name="firstName" type="text"/>
                    </p>
                    <p>
                    <label for="lasName-input"><fmt:message key="msg.user.lastname"/>: </label>
                    <input id="lastName-input" name="lastName" type="text"/>
                    </p>
                    <p>
                    <label for="age-input"><fmt:message key="msg.user.age"/>: </label>
                    <input id="age-input" name="age" type="number"/>
                    </p>
                    <p>
                    <label for="email-input"><fmt:message key="msg.user.email"/>: </label>
                    <input id="email-input" name="email" type="email"/>
                    </p>
                    <p>
                    <label for="password-input"><fmt:message key="msg.user.password"/>: </label>
                    <input id="password-input" name="password" type="password" minlength="4"/>
                    </p>
                    <p>
                    <input type="submit" value=<fmt:message key="msg.nav.register"/>
                    </p>
                </form>
        </div>
    </body>
</html>