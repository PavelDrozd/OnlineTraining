<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1><spring:message code="msg.user.profile"/></h1>
            <p>
                <spring:message code="msg.user.name"/>: <c:out value="${sessionScope.user.personalInfo.firstName}"/> <c:out value="${sessionScope.user.personalInfo.lastName}"/>
            </p>
            <p>
                <spring:message code="msg.user.personalinfo.email"/>: <c:out value="${sessionScope.user.personalInfo.email}"/>
            </p>
            <p>
                <spring:message code="msg.user.role"/>: <c:out value="${sessionScope.user.role.toString()}"/>
            </p>
        </div>
    </body>
</html>