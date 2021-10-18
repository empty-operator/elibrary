<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="header.label.loans"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>

<main>

    <%@ include file="header.jspf" %>

    <div class="py-5 bg-light">
        <div class="container">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="loan.label.book-title"/></th>
                    <th scope="col"><fmt:message key="loan.label.loaned-at"/></th>
                    <th scope="col"><fmt:message key="loan.label.due-date"/></th>
                    <th scope="col"><fmt:message key="loan.label.returned-at"/></th>
                    <th scope="col"><fmt:message key="loan.label.fine"/></th>
                    <th scope="col"><fmt:message key="loan.label.status"/></th>
                </tr>
                </thead>
                <tbody class="text-truncate">
                <c:forEach items="${requestScope.list_of_loans}" var="loan">
                    <tr>
                        <td><c:out value="${loan.book.title}"/></td>
                        <td><c:out value="${loan.loanedAt}"/></td>
                        <td><c:out value="${loan.dueDate}"/></td>
                        <td><c:out value="${loan.returnedAt}"/></td>
                        <td class="text-danger">
                            <c:if test="${loan.getFine() gt 0}">
                                <c:out value="$${loan.getFine()}"/>
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${loan.rejected}"><fmt:message key="loan.label.status.rejected"/></c:when>
                                <c:when test="${empty loan.loanedAt}"><fmt:message key="loan.label.status.in-process"/></c:when>
                                <c:when test="${empty loan.returnedAt}"><fmt:message key="loan.label.status.issued"/></c:when>
                                <c:otherwise><fmt:message key="loan.label.status.returned"/></c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

</main>

<%@ include file="footer.jspf" %>

</body>
</html>
