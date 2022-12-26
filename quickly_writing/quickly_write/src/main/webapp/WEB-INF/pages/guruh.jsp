<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guruh</title>
</head>
<body>
<div>
    Name = ${name}
    <table>
        <tr>
            <td>
                <h1>Yangi guruh yarating yoki qo'shiling</h1>
                ${message}
                <form action="/guruhWindow" method="get">
                    <input type="text" name="name" placeholder="Guruh nomi">
                    <input type="text" name="password" placeholder="Parol"><br>
                    <button type="submit">Ok</button>
                </form>
            </td>
            <td>
<%--                <h1>Guruhga qo'shilish</h1>--%>
<%--                <form action="/search" method="post">--%>
<%--                    <input name="search" type="search" placeholder="Qidirish">--%>
<%--                    <button type="submit">Ok</button>--%>
<%--                </form>--%>

<%--                <ul>--%>
<%--                    <li>--%>
<%--                        Bu yerda guruhlar listi bo'ladi...--%>
<%--                    </li>--%>
<%--                </ul>--%>
            </td>
        </tr>
    </table>
</div>
</body>
</html>