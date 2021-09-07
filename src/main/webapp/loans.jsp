<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Loans</title>

    <link href="css/loans.css" rel="stylesheet">
    <link href="dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="dist/js/bootstrap.bundle.min.js" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>

</head>
<body>

<main>

    <%@ include file="header.jspf" %>

    <div class="py-5 bg-light">
        <div class="container">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Book title</th>
                    <th scope="col">Status</th>
                    <th scope="col">Manage</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.list_of_loans}" var="loan">
                    <tr>
                        <td><c:out value="${loan.user.firstName} ${loan.user.lastName}"/></td>
                        <td><c:out value="${loan.book.title}"/></td>
                        <c:choose>
                            <c:when test="${loan.rejected}">
                                <td>
                                    Rejected
                                </td>
                                <td></td>
                            </c:when>
                            <c:when test="${empty loan.loanedAt}">
                                <td>
                                    In process
                                </td>
                                <td>
                                    <form action="LoanManagementServlet" class="form-loan d-flex align-items-center justify-content-between" method="post">
                                        <input name="loan_id" type="hidden" value="${loan.id}">
                                        <div>
                                            <input name="date" type="date" class="form-control" id="date" placeholder="Date">
                                            <label for="date" hidden>Date</label>
                                        </div>
                                        <button class="w-25 btn btn-success" type="submit" name="action" value="approve">
                                            Approve
                                        </button>
                                        <button class="w-25 btn btn-danger" type="submit" name="action" value="reject">
                                            Reject
                                        </button>
                                    </form>
                                </td>
                            </c:when>
                            <c:when test="${empty loan.returnedAt}">
                                <td>
                                    Issued
                                </td>
                                <td>
                                    <form action="LoanManagementServlet" class="form-loan" method="post">
                                        <input name="loan_id" type="hidden" value="${loan.id}">
                                        <button class="btn btn-primary float-end" type="submit" name="action" value="return">
                                            Return
                                        </button>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    Returned
                                </td>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
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
