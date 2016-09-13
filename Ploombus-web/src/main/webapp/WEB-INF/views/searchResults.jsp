<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>Ploombus</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/css.css"/>

</head>

<body>
<div class="wraper">

    <div class="header-result">
        <div class="logo-header">
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
        <form action="/search" method="post">

            <div class="result-text-input">
                <input type="text" id="search" name="q" value="${query}"/>
            </div>
            <div class="button-result">
                <button class="lsb" value="ua.tifoha.search" aria-label=" Ploomber" name="btnG" type="submit">
                    <span class="sbico"></span>
                </button>
            </div>
        </form>

    </div>
    <div class="content">
        <c:if test="${not empty searchResults}">
            <div class="result">
                <c:forEach items="${searchResults}" var="result">
                    <div class="result-0">
                        <h3><a href="${result.url}">${result.title}</a></h3>
                        <p class="link">${result.url}</p>
                        <p>${result.content}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

</div>
</body>
</html>
