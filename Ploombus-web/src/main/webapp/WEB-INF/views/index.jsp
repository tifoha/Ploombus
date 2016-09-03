<%--
  Created by IntelliJ IDEA.
  User: Vitaly
  Date: 03.09.2016
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="button.index" var="buttonIndex"/>
<html>
<head>
    <title>Ploombus</title>
</head>
    <body>
        <form target="/index" method="post">
            <div><spring:message code="label.index"/></div>
            <div><input name="q"></div>
            <div><input type="submit" name="search" value="${buttonIndex}"></div>
        </form>
    </body>
</html>
