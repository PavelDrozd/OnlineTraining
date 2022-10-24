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
        <title>Users</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <p><c:out value="${requestScope.message}"/></p>
            <table class="table table-bordered border-primary my-2">
                <tr>
                    <th><fmt:message key="msg.hashtag"/></th>
                    <th><fmt:message key="msg.user.firstname"/></th>
                    <th><fmt:message key="msg.user.lastname"/></th>
                    <th><fmt:message key="msg.user.age"/></th>
                    <th><fmt:message key="msg.user.email"/></th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <td><c:out value="${counter.count}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.age}"/></td>
                        <td><a href="/user/${user.id}"><c:out value="${user.email}"/></a></td>
                    </tr>
                </c:forEach>
            </table>
            <jsp:include page="../paging/pagination.jsp"/>
        </div>
    </body>
</html>
