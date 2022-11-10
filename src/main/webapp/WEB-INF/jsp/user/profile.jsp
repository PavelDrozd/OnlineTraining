<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1><spring:message code="msg.user.profile"/></h1>
                <p>
                    <spring:message code="msg.user.name"/>: <c:out value="${sessionScope.user.personalInfo.firstName}"/> <c:out value="${sessionScope.user.personalInfo.lastName}"/>
                    <a href="/edit_name" class="btn btn-secondary btn-sm"><spring:message code="msg.user.profile.edit.name"/></a>
                </p>
                <p>
                    <spring:message code="msg.user.personalinfo.email"/>: <c:out value="${sessionScope.user.personalInfo.email}"/>
                    <a href="/edit_email" class="btn btn-secondary btn-sm"><spring:message code="msg.user.profile.edit.email"/></a>
                </p>
                <p>
                    <spring:message code="msg.user.role"/>: <c:out value="${sessionScope.user.role.toString()}"/>
                </p>
                <a href="/edit_password" class="btn btn-secondary btn-sm"><spring:message code="msg.user.profile.edit.password"/></a>
        </div>
    </body>
</html>