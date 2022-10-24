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
        <title>Course</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <p><c:out value="${requestScope.message}"/></p>
            <table class="table table-bordered border-primary my-2">
                <tr><th><fmt:message key="msg.field"/></th><th><fmt:message key="msg.value"/></th></tr>
                <tr><td><fmt:message key="msg.id"/></td><td><c:out value="${course.id}"/></td><tr>
                <tr><td><fmt:message key="msg.course.course"/></td><td><c:out value="${course.name}"/></td><tr>
                <tr><td><fmt:message key="msg.course.cost"/></td><td><c:out value="${course.cost}"/></td><tr>
                <tr><td><fmt:message key="msg.course.duration.days"/></td><td><c:out value="${course.durationDays}"/></td><tr>
            </table>
        </div>
    </body>
</html>
