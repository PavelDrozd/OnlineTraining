<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Users</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <p><c:out value="${requestScope.message}"/></p>
            <table class="table table-bordered border-primary my-2">
                <tr>
                    <th>#</th>
                    <th>Course</th>
                    <th>Cost</th>
                    <th>Duration (days)</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${courses}" var="course" varStatus="counter">
                    <tr>
                        <td><c:out value="${counter.count}"/></td>
                        <td><a href="controller?command=course&id=${course.id}"><c:out value="${course.name}"/></a></td>
                        <td><c:out value="${course.cost}"/></td>
                        <td><c:out value="${course.durationDays}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
