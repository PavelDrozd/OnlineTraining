<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Error</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
    </head>
    <body>
        <jsp:include page="navbar/navbar.jsp"/>
        <h2><spring:message code="msg.error.error"/></h2>
        <h3><c:out value="${requestScope.error}"/></h3>
        <h2><spring:message code="msg.error.message"/></h2>
        <h3><c:out value="${requestScope.message}"/></h3>
    </body>
</html>