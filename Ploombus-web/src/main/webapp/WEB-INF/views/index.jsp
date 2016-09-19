<%--
  Created by IntelliJ IDEA.
  User: Vitaly
  Date: 11.09.2016.
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<spring:message code="button.index" var="buttonIndex"/>
<spring:url value="/index" var="indexUrl" />
<html>
<jsp:include page="header.jsp"/>
<body>
<form:form method="post" modelAttribute="crawlRequest" action="${indexUrl}">
    <table border="0">
        <tr>
            <td colspan="2" align="center"><h2><spring:message code="label.index"/></h2></td>
        </tr>
        <tr>
            <td>URL:</td>
            <td><form:input path="url"/></td>
        </tr>
        <tr>
            <td>Max depth of crawling:</td>
            <td><form:input path="maxDepthOfCrawling"/></td>
        </tr>
        <tr>
            <td>Max outgoing links:</td>
            <td><form:input path="maxOutgoingLinksToFollow"/></td>
        </tr>
        <tr>
            <td>Total pages count:</td>
            <td><form:input path="maxPagesToFetch"/></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" value="Index" /></td>
        </tr>
    </table>
</form:form>
<c:if test="${not empty crawlResponseList}">
    <br>
    <h2>Current crawling tasks</h2>
    <table border="1">
        <thead>
        <tr>
            <td>URL</td>
            <td>Processed pages</td>
            <td>Links count</td>
            <td>Data size</td>
            <td>Finished</td>
            <td>&nbsp;</td>
        </tr>
        </thead>
        <c:forEach var="crawlResponse" items="${crawlResponseList}">
            <c:set var="crawlStatus" value="${crawlResponse.status}"/>
            <tr>
                <td>${crawlResponse.url}</td>
                <td>${crawlStatus.totalProcessedPages}</td>
                <td>${crawlStatus.totalLinks}</td>
                <td>${crawlStatus.totalDataSize}</td>
                <td>${crawlResponse.finished}</td>
                <td>
                    <c:if test="${not crawlResponse.finished}">
                        <form action="${indexUrl}" method="get">
                            <input type="hidden" name="url" value="${crawlResponse.url}"/>
                            <input type="submit" name="stop" value="Stop"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="6" align="right">
                <form action="${indexUrl}" method="get">
                    <input type="submit" name="refresh" value="Refresh"/>
                </form>
            </td>
        </tr>
    </table>
</c:if>

</body>
</html>
