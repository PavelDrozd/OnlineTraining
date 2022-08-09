<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register new user</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script defer src="js/script.js"></script>
    </head>
    <body>
        <jsp:include page="../navbar/navbar.jsp"/>
        <div class="container text-left my-2">
            <h1> Register </h1>
                <form method="post" action="controller">
                    <p>
                    <input name="command" type="hidden" value="create_user_form"/>
                    <label for="firstName-input">First name: </label>
                    <input id="firstName-input" name="firstName" type="text"/>
                    </p>
                    <p>
                    <label for="lasName-input">Last name: </label>
                    <input id="lastName-input" name="lastName" type="text"/>
                    </p>
                    <p>
                    <label for="age-input">Age: </label>
                    <input id="age-input" name="age" type="number"/>
                    </p>
                    <p>
                    <label for="email-input">Email: </label>
                    <input id="email-input" name="email" type="email"/>
                    </p>
                    <p>
                    <label for="password-input">Password: </label>
                    <input id="password-input" name="password" type="password" minlength="4"/>
                    </p>
                    <p>
                    <input type="submit" value="Register"/>
                    </p>
                </form>
        </div>
    </body>
</html>