<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.language != null}">
<fmt:setLocale value="${sessionScope.language}"/>
</c:if>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/"><fmt:message key="msg.nav.title"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/course/list"><fmt:message key="msg.nav.courses"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/list"><fmt:message key="msg.nav.users"/></a>
                </li>

                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/order/list"><fmt:message key="msg.nav.orders"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/profile"><fmt:message key="msg.nav.profile"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/logout"><fmt:message key="msg.nav.logout"/></a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/login"><fmt:message key="msg.nav.login"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/register"><fmt:message key="msg.nav.register"/></a>
                    </li>
                </c:if>

        </div>
    </div>
</nav>