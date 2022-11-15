<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Course</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <p><c:out value="${requestScope.message}"/></p>
            <table class="table table-bordered border-primary my-2">
                <tr><th><spring:message code="msg.field"/></th><th><spring:message code="msg.value"/></th></tr>
                <tr><td><spring:message code="msg.id"/></td><td><c:out value="${course.id}"/></td><tr>
                <tr><td><spring:message code="msg.course.course"/></td><td><c:out value="${course.name}"/></td><tr>
                <tr><td><spring:message code="msg.course.cost"/></td><td><c:out value="${course.cost}"/></td><tr>
                <tr><td><spring:message code="msg.course.duration.days"/></td><td><c:out value="${course.durationDays}"/></td><tr>
            </table>
        </div>
    </body>
</html>
