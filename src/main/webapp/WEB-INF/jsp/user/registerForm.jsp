<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register new user</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
        <script defer src="/js/jquery-3.6.1.js"></script>
        <script defer src="/js/user/register.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1> <spring:message code="msg.user.register"/> </h1>
            <p><c:out value="${requestScope.message}"/></p>
            <form class="input-form">
                <label for="input-firstname"><spring:message code="msg.user.personalinfo.firstname"/></label>
                <input id="input-firstname" type="text" required="true"/>
                <br/>
                <label for="input-lastname"><spring:message code="msg.user.personalinfo.lastname"/></label>
                <input id="input-lastname" type="text" required="true"/>
                <br/>
                <label for="input-patronymic"><spring:message code="msg.user.personalinfo.patronymic"/></label>
                <input id="input-patronymic" type="text"/>
                <br/>
                <label for="input-email"><spring:message code="msg.user.personalinfo.email"/></label>
                <input id="input-email" type="email" required="true"/>
                <br/>
                <label for="input-login"><spring:message code="msg.user.login"/></label>
                <input id="input-login" type="text" required="true"/>
                <br/>
                <label for="input-password"><spring:message code="msg.user.password"/></label>
                <input id="input-password" type="password" required="true"/>
                <br/>
                <button class="create"><spring:message code="msg.user.register"/></button>
            </form>
        </div>
    </body>
</html>