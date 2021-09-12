<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Loans</title>

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
                    <th scope="col">Book title</th>
                    <th scope="col">Loaned at</th>
                    <th scope="col">Due date</th>
                    <th scope="col">Returned at</th>
                    <th scope="col">Fine</th>
                    <th scope="col">Status</th>
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
                                <c:when test="${loan.rejected}">
                                    Rejected
                                </c:when>
                                <c:when test="${empty loan.loanedAt}">
                                    In process
                                </c:when>
                                <c:when test="${empty loan.returnedAt}">
                                    Issued
                                </c:when>
                                <c:otherwise>
                                    Returned
                                </c:otherwise>
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
