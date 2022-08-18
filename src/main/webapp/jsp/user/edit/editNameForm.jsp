<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit name</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1><fmt:message key="msg.user.edit"/><fmt:message key="msg.user.name.l"/></h1>
            <form method="post" action="controller">
                <p>
                <input name="command" type="hidden" value="edit_name"/>
                <label for="firstName-input"><fmt:message key="msg.user.firstname"/>: </label>
                <input id="firstName-input" name="firstName" type="text" value="${requestScope.user.firstName}"/>
                </p>
                <p>
                <label for="lasName-input"><fmt:message key="msg.user.lastname"/>: </label>
                <input id="lastName-input" name="lastName" type="text" value="${requestScope.user.lastName}"/>
                </p>
                <p>
                <input type="submit" value=<fmt:message key="msg.user.edit"/>/>
                </p>
            </form>
        </div>
    </body>
</html>