<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--@elvariable id="welcome" type="messages"--%>

<!DOCTYPE html>
<html lang="uz">
<head>
    <meta charset="UTF-8">
    <title>Mumtozbegim.uz</title>
    <style>
        body {
            background-image: url("../webapp/resources/static/images/bg_3.png");
        }
    </style>
    <link rel="stylesheet" href="webjars/bootstrap/5.1.3/css/bootstrap.css">
</head>
<body style="min-height: 600px">
<button style="margin: 10px" class="btn btn-outline-success">bos</button>
<div>
    <header>
        <table>
            <tr>
                <td>
                    <a href="/"><input type="button" class="btn btn-outline-success" style="font-size: 20pt" href="/" value="Tezyozish.uz"></a>
                </td>
                <td style="">
                    <a href="sinov"><button class="btn btn-outline-info">Sinov rejimi</button></a>
                </td>

                <td style="">
                    <a href="guruh"><button class="btn btn-outline-info">Guruh rejimi</button></a>
                </td>

                <td>

                    <a style="visibility: ${visiblity}; font-size: 16pt; background: cadetblue; border-radius: 5px;padding: 5px;width: 50px !important;"
                       href="profil"><button class="btn btn-outline-info">${userName}</button></a>
                </td>
                <td>
                    <a href="register_form"><button class="btn btn-outline-info">Registration</button></a>
                </td>
                <td>
                    <a href="register_form"><button class="btn btn-outline-info">Registration</button></a>

                    <a href="/login_form">login</a>
                </td>
                <td>
                    <%--This example for HTML--%>
                    <%--                    <h1 th:text="#{greeting}"></h1>--%>

                    <h1><spring:message code="greeting" text="default"/></h1>
                    <%--                        <h1 th:text="#{greeting}"></h1>--%>

                </td>
                <span><spring:message code="lang.change" text="default"/></span>:
                <select id="locales" class="form-check-label">
                    <option value="en"><spring:message code="lang.en" text="default"/></option>
                    <option value="uz"><spring:message code="lang.uz" text="default"/></option>
                </select>
            </tr>
        </table>
        <div>
        </div>
    </header>
</div>
${message}
<br>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<script type="text/javascript">
    let selectedOption;
    $(document).ready(function () {
        $("#locales").change(function () {
            selectedOption = $('#locales').val();
            if (selectedOption !== '') {

                window.location.replace('?lang=' + selectedOption);

            }
        });
    });

    selId = document.getElementById("locales");
    if (window.location.href === "http://localhost:9090/?lang=uz"){
        selId.value = 'uz';
        console.log("birinchisi");
    }else {
        selId.value = 'en';
        console.log("Ikkinchi");
    }

</script>
</body>
</html>
