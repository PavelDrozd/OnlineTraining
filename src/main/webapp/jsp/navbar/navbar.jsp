<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/onlinetraining/">Online training</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="controller?command=logout">Logout</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="controller?command=login_form">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=create_user_form">Register</a>
                    </li>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link" href="controller?command=courses">Courses</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="controller?command=orders">Orders</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="controller?command=users">Users</a>
                </li>

        </div>
    </div>
</nav>