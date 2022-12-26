<%--@elvariable id="userNames" type="uz.quickly_write.service.GroupService"--%>
<%--@elvariable id="userNames2" type="uz.quickly_write.service.GroupService"--%>
<%--@elvariable id="one" type="uz.quickly_write.service.GroupService"--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">

</head>
<body>

</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guruh Musoboqasi</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>

    <script src="../../resources/static/plugins/progressBar/dist/jquery.countdown360.min.js"></script>
    <style>
        #typingPlace {
            /*border-color: white;*/
            border-bottom-color: #118170;
            border: 3px solid;
            border-bottom-right-radius: 2px;
            border-bottom-left-radius: 2px;
            border-left-color: transparent;
            border-right-color: transparent;
            border-top-color: transparent;
        }

        #typingPlace:focus {
            outline: none;
        }
    </style>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Quicksand">

</head>
<body style="min-height: 600px; background-color: white">
<h3 style="position: absolute;left: 200px">
    Guruh nomi: ${gName}<br>
    Parol: ${gPass}
</h3>
NAME : ${userName}
<br>
Names :
<p th:each="one : ${userNames}">
    <p th:text="${one}"></p>

    </p>



<p>
    <c:forEach var="var" items="${userNames}">
        <c:out value="${var}"/> <br> <br>
    </c:forEach>
</p>
<%

    String name=request.getParameter("userNames2");
    out.print("Welcome "+name);
    %>


    <div id="result"
     style=" text-align:center; width: 500px; height: 200px;position: absolute; margin-left: 420px; border-radius: 30px    ">
    <p id="pResult"
       style="border-radius:20px; font-family: Arial; padding-top: 20px;padding-bottom: 20px; font-size: 20pt">

    </p>
</div>

<%--TIMER--%>
<div id="container">
    <div id="countdown">
        <canvas id="countdown360_countdown" width="150" height="150"><span id="countdown-text" role="status"
                                                                           aria-live="assertive"></span></canvas>
    </div>
</div>

<div>
    <%--INPUT FIELD--%>
    <input type="text" name="typingPlace" id="typingPlace"
           style="width: 20%;padding: 5px; font-size: 22pt;margin-left: 530px;margin-top: 50px;">
    <div>
        <%--TYPED VALUES--%>
        <p id="values"
           style="font-size: 20pt;user-select: none;font-family: Quicksand;position: absolute;margin: 100px; z-index: 100; color: #3fd942;background-color: rgba(255,255,255,0)"></p>

        <%--MAIN TEXT--%>
        <p id="orgText"
           style="font-size: 20pt; -webkit-user-select: all; user-select: none; font-family: Quicksand; margin: 100px; position: absolute; color: #484747; z-index: 0">
            ${text}
        </p>
    </div>
</div>


<%--INPUT FIELD SCRIPTS--%>
<script>
    const input = document.getElementById("typingPlace");
    const pResult = document.getElementById("pResult");
    const successed = document.getElementById('values');
    var mainText = document.getElementById("orgText");
    var orgText = mainText.textContent.trim().split(' ');

    input.focus();
    input.addEventListener('input', updateValue);
    let cntCorrectWords = 0;
    let cntChanging = 0;

    function updateValue(e) {
        cntChanging++;
        if (cntChanging === 1) {
            countdown.start();
        }
        let text = e.target.value;
        if (orgText[0].startsWith(text.trim())) {
            input.style.color = 'green';
        } else {
            input.style.color = 'red';
        }
        if (text.endsWith(' ') && text.trim() === orgText[0]) {
            orgText.shift()
            successed.textContent = successed.textContent + ' ' + text.trim();
            input.value = '';
            cntCorrectWords++;
            console.log(orgText);
        }
    }
</script>

<%--PROGRESS BAR SCRIPTS--%>
<script type="text/javascript" charset="utf-8">
    var countdown = $("#countdown").countdown360({

        radius: 70,                    // radius of arc
        strokeStyle: "#070707",          // the color of the stroke
        strokeWidth: undefined,          // the stroke width, dynamically calulated if omitted in options
        fillStyle: "#8ac575",            // the fill color
        fontColor: "#477050",            // the font color
        fontFamily: "sans-serif",        // the font family
        fontSize: undefined,             // the font size, dynamically calulated if omitted in options
        fontWeight: 700,                 // the font weight
        autostart: false,                 // start the countdown automatically
        seconds: 60,                     // the number of seconds to count down
        label: ["second", "seconds"],    // the label to use or false if none
        startOverAfterAdding: true,      // Start the timer over after time is added with addSeconds
        smooth: true,                   // should the timer be smooth or stepping
        onComplete: function () {
            input.setAttribute("readonly", "true");
            pResult.textContent = "Yozish tezligingiz : " + cntCorrectWords + " WPM";
            pResult.style.background = 'rgba(13,234,17,0.2)';
        }
    });
</script>
</body>
</html>