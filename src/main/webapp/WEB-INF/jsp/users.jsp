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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

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
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Role</th>
                    <th scope="col">Banned</th>
                    <th scope="col">Fine</th>
                    <th scope="col">Manage</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.list_of_users}" var="user">
                    <c:if test="${not (user.role eq Role.ADMIN)}">
                        <tr>
                            <td><c:out value="${user.firstName}"/></td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td>${user.banned ? "Yes" : "No"}</td>
                            <td><c:out value="fine"/></td>
                            <td>
                                <form class="form-loan d-flex align-items-center justify-content-between"
                                      action="UserManagementServlet"
                                      method="post">
                                    <input name="user-id" type="hidden" value="${user.id}">
                                    <c:if test="${user.role eq Role.READER}">
                                        <button name="action" value="make-librarian" type="submit"
                                                class="btn btn-primary w-50">Make librarian
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role eq Role.LIBRARIAN}">
                                        <button name="action" value="make-reader" type="submit"
                                                class="btn btn-primary w-50">
                                            Make reader
                                        </button>
                                    </c:if>
                                    <c:if test="${not user.banned}">
                                        <button name="action" value="ban" type="submit" class="btn btn-danger w-25">Ban
                                        </button>
                                    </c:if>
                                    <c:if test="${user.banned}">
                                        <button name="action" value="unban" type="submit" class="btn btn-danger w-25">
                                            Unban
                                        </button>
                                    </c:if>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

</main>

<%@ include file="footer.jspf" %>

</body>
</html>
