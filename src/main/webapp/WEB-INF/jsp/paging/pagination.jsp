<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a class="btn btn-outline-success" href="controller?command=users&page=1">
    First
</a>
<a class="btn btn-outline-secondary"  href="controller?command=users&page=${requestScope.currentPage}">
    Prev
</a>

<spring:message code="msg.pag.page"/> ${requestScope.currentPage} <spring:message code="msg.pag.outof.l"/> ${requestScope.totalPages}

<a class="btn btn-outline-secondary" href="controller?command=users&page=${requestScope.currentPage}">
    Next
</a>
<a class="btn btn-outline-success" href="controller?command=users&page=${requestScope.totalPages}">
    Last
</a>