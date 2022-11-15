<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit user</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <script defer src="/js/bootstrap-5.2.0.js"></script>
        <script defer src="/js/jquery-3.6.1.js"></script>
        <script type="module" src="/js/user/edit_profile.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1><spring:message code="msg.user.profile.edit"/></h1>
            <form class="input-form">
                <input id="input-id" value="${user.id}" type="hidden">
                <label for="input-firstname"><spring:message code="msg.user.personalinfo.firstname"/></label>
                <input id="input-firstname" type="text"/>
                <br/>
                <label for="input-lastname"><spring:message code="msg.user.personalinfo.lastname"/></label>
                <input id="input-lastname" type="text"/>
                <br/>
                <label for="input-email"><spring:message code="msg.user.personalinfo.email"/></label>
                <input id="input-email" type="email"/>
                <br/>
                <label for="input-email"><spring:message code="msg.user.personalinfo.patronymic"/></label>
                <input id="input-email" type="text"/>
                <br/>
                <label for="input-firstname"><spring:message code="msg.user.login"/></label>
                <input id="input-firstname" type="text"/>
                <br/>
                <label for="input-lastname"><spring:message code="msg.user.password"/></label>
                <input id="input-lastname" type="password"/>
                <br/>
                <button class="edit"><spring:message code="msg.user.edit"/></button>
            </form>
        </div>
    </body>
</html>