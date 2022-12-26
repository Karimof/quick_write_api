<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="../../resources/static/css/loginForm.css" rel="stylesheet" type="text/css">

</head>

<body>
<div>
    <div align="center" style="margin-top: 15%;">
        <form action="/register" method="post">
            <h3>Registration</h3>
            <p>${message}</p>
            <input type="text" name="fullName" placeholder="Ism Familiya" value="${fullName}"><br>
            <input type="text" name="userName" placeholder="Login"><br>
            <input type="email" name="email" placeholder="masalan@gmail.com"><br>
            <input type="password" name="password" placeholder="Parol"><br>
            <input type="password" name="checkPassword" placeholder="Parolni takrorlang"><br>
            <button type="submit">Ok</button>
        </form>
    </div>
</div>
</body>

</html>

<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--${message}--%>
<%--<form action="/register" method="post">--%>
<%--    <h3>Registration</h3>--%>
<%--    <input type="text" name="fullName" placeholder="Ism Familiya"><br>--%>
<%--    <input type="text" name="userName" placeholder="Login"><br>--%>
<%--    <input type="email" name="email" placeholder="masalan@gmail.com"><br>--%>
<%--    <input type="password" name="password" placeholder="Parol"><br>--%>
<%--    <input type="password" name="checkPassword" placeholder="Parolni takrorlang"><br>--%>
<%--    <button type="submit">Ok</button>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>