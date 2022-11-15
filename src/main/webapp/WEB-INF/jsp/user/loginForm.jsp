<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-4">
            <h1><spring:message code="msg.nav.login"/></h1>
            <p><c:out value="${requestScope.message}"/></p>
            <form:form class="login-form" method="post" action="/users/login" modelAttribute="user">
                <form:errors path="login" align-self="start" color="red"/>
                <form:errors path="password" align-self="start" color="red"/>
                <form:label path="login"><spring:message code="msg.user.login"/><form:input path="login" type="text" required="true"/></form:label>
                <form:label path="password"><spring:message code="msg.user.password"/><form:input path="password" type="password" required="true"/></form:label>
                <form:button><spring:message code="msg.user.login.l"/></form:button>
            </form:form>
        </div>
    </body>
</html>