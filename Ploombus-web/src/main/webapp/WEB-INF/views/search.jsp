<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="button.search" var="buttonSearch"/>

<!DOCTYPE html>
<html>

<head>
    <%--<meta charset="UTF-8" />--%>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <title>Ploombus</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/css.css"/>

</head>

<body>
<div class="wraper">

    <div class="header">

    </div>

    <div class="content">
        <div class="logo">
            <p>
                <span class="bloo">P</span>
                <span class="red">l</span>
                <span class="yellow">o</span>
                <span class="bloo">o</span>
                <span class="green">m</span>
                <span class="yellow">b</span>
                <span class="red">e</span>
                <span class="green">r</span>
            </p>
        </div>
        <div class="form">
            <form action="/search" method="post">
                <div class="text-input">
                    <center>
                        <input type="text" id="search" name="q" value=""/>
                    </center>
                </div>
                <div class="button" style="padding-top:18px">
                    <center>
                        <input value="${buttonSearch} Ploomber" aria-label="${buttonSearch} Ploomber" name="btnK"
                               type="submit">
                    </center>
                </div>
            </form>
        </div>
        <div class="text">
            <p><a href="/?lang=en">English</a>|<a href="/?lang=ru">Русский</a></p>
        </div>
    </div>


</div>
</body>
</html>


<%--&lt;%&ndash;--%>
<%--Created by IntelliJ IDEA.--%>
<%--User: Vitaly--%>
<%--Date: 11.09.2016.--%>
<%--Time: 20:18--%>
<%--To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--&lt;%&ndash;<%@ page contentType="text/html;charset=UTF-8" language="java" %>&ndash;%&gt;--%>
<%--<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>--%>
<%--<spring:message code="button.ua.tifoha.search" var="buttonSearch"/>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>

<%--<head>--%>

<%--<meta charset="UTF-8" />--%>
<%--&lt;%&ndash;<meta http-equiv="X-UA-Compatible" content="IE=edge">&ndash;%&gt;--%>
<%--<title>Ploomber</title>--%>
<%--<link rel="stylesheet" type="text/css" href="/resources/css/css.css" />--%>

<%--</head>--%>

<%--<body>--%>
<%--<div class="wraper">--%>

<%--<div class="header">--%>

<%--</div>--%>

<%--<div class="content">--%>
<%--<div class="logo">--%>
<%--<img src="img/doogle.jpeg" alt="ploomber">--%>
<%--</div>--%>
<%--<div class="form">--%>
<%--<div class="text-input">--%>
<%--<center>--%>
<%--<input type="text" id="q" name="user-name" value=""/>--%>
<%--</center>--%>
<%--</div>--%>
<%--<div class="button" style="padding-top:18px">--%>
<%--<center>--%>
<%--<input value="${buttonSearch} Ploomber" aria-label="${buttonSearch} Ploomber" name="btnK" type="submit" >--%>
<%--&lt;%&ndash;<input value="Мені пощастить" aria-label="Мені пощастить" name="btnI" type="submit" jsaction="sf.lck">&ndash;%&gt;--%>
<%--</center>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="text">--%>
<%--<p>ploomber.com.ua на <a href="#?lang=ru">русский${lang}</a></p>--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="footer"></div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>


<%--&lt;%&ndash;<html>&ndash;%&gt;--%>
<%--&lt;%&ndash;<head>&ndash;%&gt;--%>
<%--&lt;%&ndash;<title>Ploombus</title>&ndash;%&gt;--%>
<%--&lt;%&ndash;</head>&ndash;%&gt;--%>
<%--&lt;%&ndash;<body>&ndash;%&gt;--%>
<%--&lt;%&ndash;<form target="/ua.tifoha.search" method="post">&ndash;%&gt;--%>
<%--&lt;%&ndash;<div><spring:message code="label.ua.tifoha.search"/></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div><input name="q"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div><input type="submit" name="ua.tifoha.search" value="${buttonSearch}"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>
<%--&lt;%&ndash;</body>&ndash;%&gt;--%>
<%--&lt;%&ndash;</html>&ndash;%&gt;--%>
