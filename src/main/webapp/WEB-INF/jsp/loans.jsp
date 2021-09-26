<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Loans</title>

    <link href="css/loans.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>

<main>

    <%@ include file="header.jspf" %>

    <div class="py-5 bg-light">
        <div class="container">

            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="in-process-tab" data-bs-toggle="tab" data-bs-target="#in-process" type="button" role="tab" aria-controls="in-process" aria-selected="true">In process</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="issued-tab" data-bs-toggle="tab" data-bs-target="#issued" type="button" role="tab" aria-controls="issued" aria-selected="false">Issued</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="returned-tab" data-bs-toggle="tab" data-bs-target="#returned" type="button" role="tab" aria-controls="returned" aria-selected="false">Returned</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="rejected-tab" data-bs-toggle="tab" data-bs-target="#rejected" type="button" role="tab" aria-controls="rejected" aria-selected="false">Rejected</button>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane fade show active" id="in-process" role="tabpanel" aria-labelledby="in-process-tab">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">First name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Book title</th>
                            <th scope="col">Manage</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.list_of_loans}" var="loan">
                            <c:if test="${empty loan.loanedAt and not loan.rejected}">
                                <tr>
                                    <td><c:out value="${loan.user.firstName}"/></td>
                                    <td><c:out value="${loan.user.lastName}"/></td>
                                    <td><c:out value="${loan.book.title}"/></td>
                                    <td>
                                        <form action="LoanManagementServlet"
                                              class="form-loan d-flex align-items-center justify-content-between"
                                              method="post">
                                            <input name="loan-id" type="hidden" value="${loan.id}">
                                            <div>
                                                <input name="date" type="date" class="form-control" id="date"
                                                       placeholder="Date">
                                                <label for="date" hidden>Date</label>
                                            </div>
                                            <button class="w-25 btn btn-success" type="submit" name="action"
                                                    value="approve">
                                                Approve
                                            </button>
                                            <button class="w-25 btn btn-danger" type="submit" name="action"
                                                    value="reject">
                                                Reject
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="issued" role="tabpanel" aria-labelledby="issued-tab">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">First name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Book title</th>
                            <th scope="col">Loaned at</th>
                            <th scope="col">Due date</th>
                            <th scope="col">Fine</th>
                            <th scope="col">Manage</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.list_of_loans}" var="loan">
                            <c:if test="${not empty loan.loanedAt and empty loan.returnedAt}">
                                <tr>
                                    <td><c:out value="${loan.user.firstName}"/></td>
                                    <td><c:out value="${loan.user.lastName}"/></td>
                                    <td><c:out value="${loan.book.title}"/></td>
                                    <td><c:out value="${loan.loanedAt}"/></td>
                                    <td><c:out value="${loan.dueDate}"/></td>
                                    <td class="text-danger">
                                        <c:if test="${loan.getFine() gt 0}">
                                            <c:out value="$${loan.getFine()}"/>
                                        </c:if>
                                    </td>
                                    <td>
                                        <form action="LoanManagementServlet" class="form-loan" method="post">
                                            <input name="loan-id" type="hidden" value="${loan.id}">
                                            <button class="btn btn-primary" type="submit" name="action"
                                                    value="return">
                                                Return
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="returned" role="tabpanel" aria-labelledby="returned-tab">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">First name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Book title</th>
                            <th scope="col">Loaned at</th>
                            <th scope="col">Due date</th>
                            <th scope="col">Returned at</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.list_of_loans}" var="loan">
                            <c:if test="${not empty loan.returnedAt}">
                                <tr>
                                    <td><c:out value="${loan.user.firstName}"/></td>
                                    <td><c:out value="${loan.user.lastName}"/></td>
                                    <td><c:out value="${loan.book.title}"/></td>
                                    <td><c:out value="${loan.loanedAt}"/></td>
                                    <td><c:out value="${loan.dueDate}"/></td>
                                    <td><c:out value="${loan.returnedAt}"/></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="rejected" role="tabpanel" aria-labelledby="rejected-tab">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">First name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Book title</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.list_of_loans}" var="loan">
                                <c:if test="${loan.rejected}">
                                    <tr>
                                        <td><c:out value="${loan.user.firstName}"/></td>
                                        <td><c:out value="${loan.user.lastName}"/></td>
                                        <td><c:out value="${loan.book.title}"/></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>

</main>

<%@ include file="footer.jspf" %>

</body>
</html>
