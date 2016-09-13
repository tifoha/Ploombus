<%--
  Created by IntelliJ IDEA.
  User: Vitaly
  Date: 11.09.2016.
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<spring:message code="button.index" var="buttonIndex"/>
<html>
<body>
<form action="/index" method="post">
    <h1><div><spring:message code="label.index"/></div></h1>
    <div>URL:<input name="q"> начальный адресс</div>
    <div>Глубина:<input name="maxDepthOfCrawling"> максимальная глубина перехода</div>
    <div>Ширина:<input name="maxOutgoingLinksToFollow"> лимит ссылок с одной страницы</div>
    <div>Лимит:<input name="maxPagesToFetch"> общий лимит страниц</div>
    <div><input type="submit" name="search" value="${buttonIndex}"></div>
</form>
<c:if test="${not empty taskList}">
    <ul>
        <c:forEach var="task" items="${taskList}">
            <li>${task.finished}</li>
        </c:forEach>

    </ul>
</c:if>

</body>
<head>
    <title>Ploombus</title>
</head>
</html>
