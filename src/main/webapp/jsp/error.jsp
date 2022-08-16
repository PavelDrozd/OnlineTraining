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
        <title>Error</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="navbar/navbar.jsp"/>

        <h1> <fmt:message key="msg.error.somethingwrong"/> </h1>
    </body>
</html>