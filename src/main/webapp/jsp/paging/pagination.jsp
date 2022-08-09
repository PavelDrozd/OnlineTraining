<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<a class="btn btn-outline-success" href="controller?command=users&page=1">
    First
</a>
<a class="btn btn-outline-secondary"  href="controller?command=users&page=${requestScope.currentPage - 1}">
    Prev
</a>

Page ${requestScope.currentPage} out of ${requestScope.totalPages}

<a class="btn btn-outline-secondary" href="controller?command=users&page=${requestScope.currentPage + 1}">
    Next
</a>
<a class="btn btn-outline-success" href="controller?command=users&page=${requestScope.totalPages}">
    Last
</a>