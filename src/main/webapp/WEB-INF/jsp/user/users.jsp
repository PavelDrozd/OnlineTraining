<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Users</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
        <script defer src="/js/jquery-3.6.1.js"></script>
        <script type="module" src="/js/users.js"></script>

    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="query-string" style="display: none"><c:out value="${requestScope.queryString}"/></div>
        <p><c:out value="${requestScope.message}"/></p>
            <div class="pagination">
                <button class="first">First</button>
                <button class="prev">Prev</button>
                <button class="current"></button>
                <button class="next">Next</button>
                <button class="last">Last</button>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th><spring:message code="msg.hashtag"/></th>
                    <th><spring:message code="msg.user.personalinfo.firstname"/></th>
                    <th><spring:message code="msg.user.personalinfo.lastname"/></th>
                    <th><spring:message code="msg.user.personalinfo.email"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
    </body>
</html>
