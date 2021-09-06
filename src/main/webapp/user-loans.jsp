<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Loans</title>

    <link href="dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="dist/js/bootstrap.bundle.min.js" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>

</head>
<body>

<main>

    <%@ include file="header.jspf"%>

    <div class="py-5 bg-light">
        <div class="container">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Book title</th>
                    <th scope="col">Loaned at</th>
                    <th scope="col">Due date</th>
                    <th scope="col">Returned at</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody class="text-truncate">
                    <c:forEach items="${requestScope.list_of_loans}" var="loan">
                        <tr>
                            <td>${loan.book.title}</td>
                            <td>${loan.loanedAt}</td>
                            <td>${loan.dueDate}</td>
                            <td>${loan.returnedAt}</td>
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

<%@ include file="footer.jspf"%>

<script src="dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
