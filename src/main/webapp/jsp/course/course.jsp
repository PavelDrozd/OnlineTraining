<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-center my-2">
            <table class="table table-bordered border-primary my-2">
                <tr><th>Field</th><th>Value</th></tr>
                <tr><td>ID</td><td>${course.id}</td><tr>
                <tr><td>Course</td><td>${course.name}</td><tr>
                <tr><td>Cost</td><td>${course.cost}</td><tr>
                <tr><td>Duration (days)</td><td>${course.durationDays}</td><tr>
            </table>
        </div>
    </body>
</html>
