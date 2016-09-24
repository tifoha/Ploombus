<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="button.search" var="buttonSearch"/>
<spring:url value="/search" var="searchUrl" />

<!DOCTYPE html>
<html>

<jsp:include page="header.jsp"/>

<body>
<div class="wraper">

    <div class="header">

    </div>

    <div class="content">
        <div class="logo">
            <p>
                <span class="blue">P</span>
                <span class="red">l</span>
                <span class="yellow">o</span>
                <span class="blue">o</span>
                <span class="green">m</span>
                <span class="yellow">b</span>
                <span class="red">e</span>
                <span class="green">r</span>
            </p>
        </div>
        <div class="form">
            <form action="${searchUrl}" method="post">
                <div class="text-input">
                    <center>
                        <input type="text" id="search" name="queryString" value=""/>
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
