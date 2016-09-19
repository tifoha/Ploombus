<%@ page import="ua.tifoha.search.searcher.SearchResult" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<spring:url value="/search" var="searchUrl" />
<!DOCTYPE html>
<html>

<jsp:include page="header.jsp"/>

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
        <form action="${searchUrl}" method="post">

            <div class="result-text-input">
                <input type="text" id="search" name="queryString" value="${queryString}"/>
            </div>
            <div class="button-result">
                <button class="lsb" value="ua.tifoha.search" aria-label=" Ploomber" name="btnG" type="submit">
                    <span class="sbico"></span>
                </button>
            </div>
        </form>

    </div>
    <div class="content">
        <c:if test="${not empty searchResult}">
            <div class="result">
                <c:forEach items="${searchResult.rows}" var="result">
                    <div class="result-0">
                        <h3><a href="${result.url}">${result.title}</a></h3>
                        <p class="link">${result.url}</p>
                        <p>${result.content}</p>
                    </div>
                </c:forEach>
                <!--                    pagination             -->
                <%--it is a bad idea to write code in the jsp, I'll burn in hell :(--%>
                <c:if test="${searchResult.totalPages > 1}">
                    <%
                        final int maxPageCount = 5;
                        final int averagePage = maxPageCount / 2;
                        SearchResult searchResult = (SearchResult) request.getAttribute("searchResult");
                        int totalPages = searchResult.getTotalPages();
                        int pageNumber = searchResult.getPageNumber();
                        int firstPage = 1;
                        int lastPage = totalPages;
                        int forwardPage = pageNumber + 1;
                        int backwardPage = pageNumber - 1;
                        if (totalPages > maxPageCount) {
                            firstPage = pageNumber > averagePage ? pageNumber - averagePage : 1;
                            lastPage = pageNumber > averagePage ? pageNumber + averagePage : maxPageCount;
                            System.out.println();
                        }
                        request.setAttribute("firstPage", firstPage);
                        request.setAttribute("lastPage", lastPage);
                        request.setAttribute("forwardPage", forwardPage);
                        request.setAttribute("backwardPage", backwardPage);
                    %>
                    <div class="pagination">
                        <p class="Pl"><span class="bloo">P</span><span class="red">l</span></p>
                        <ul>
                            <li class="back">
                                <a href="${searchUrl}?queryString=${queryString}&pageNumber=${backwardPage}&pageSize=${searchResult.pageSize}"><spring:message
                                        code="label.backward"/></a>
                            </li>
                            <c:forEach var="i" begin="${firstPage}" end="${lastPage}">
                                <li class="${(i -1) == searchResult.pageNumber ? "active" : ""}">
                                    <a href="${searchUrl}?queryString=${queryString}&pageNumber=${i - 1}&pageSize=${searchResult.pageSize}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="next">
                                <a href="${searchUrl}?queryString=${queryString}&pageNumber=${forwardPage}&pageSize=${searchResult.pageSize}"><spring:message
                                        code="label.forward"/></a>
                            </li>

                        </ul>
                        <p class="mber"><span class="green">m</span><span class="yellow">b</span><span class="red">e</span><span class="green">r</span></p>
                    </div>
                    <!-- end pagination -->
                </c:if>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
