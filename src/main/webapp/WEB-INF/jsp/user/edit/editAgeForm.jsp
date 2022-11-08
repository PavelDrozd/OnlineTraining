<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit name</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1><fmt:message key="msg.user.edit"/><fmt:message key="msg.user.age.l"/></h1>
                <form method="post" action="/edit_user_age">
                    <p>
                    <input name="command" type="hidden" value="edit_age"/>
                    <label for="age-input"><fmt:message key="msg.user.age"/>: </label>
                    <input id="age-input" name="age" type="text" value="${requestScope.user.age}"/>
                    </p>
                    <p>
                    <input type="submit" value=<fmt:message key="msg.user.edit"/>/>
                    </p>
                </form>
        </div>
    </body>
</html>