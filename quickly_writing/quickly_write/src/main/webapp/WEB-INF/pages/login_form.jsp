<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">

<head>
    <title>
        Registratsiya
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="../../resources/static/css/loginForm.css" rel="stylesheet" type="text/css">

</head>

<body>
<div>
    <div align="center" style="margin-top: 15%;">
        <form action="/login" method="post">
            <h3><spring:message code="userlogin" text="default"/></h3>
            <h5 style="color: red" align="center"> ${message}</h5>
            <input placeholder="Enter your userName" type="text" class="form-control" name="userName"><br>
            <input type="text" placeholder="Enter Password" class="form-control" name="password"><br>
            <button class="btn btn-success" type="submit">Enter</button>
        </form>
    </div>
</div>
</body>

</html>