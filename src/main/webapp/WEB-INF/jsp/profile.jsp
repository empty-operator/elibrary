<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Catalog</title>

    <link href="css/catalog.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>

<main>

    <%@ include file="header.jspf" %>

    <div class="py-5 bg-light">
        <div class="container">

            <table class="table table-hover">
                <tbody class="text-truncate">
                <tr>
                    <th scope="col"><fmt:message key="user.label.first-name"/></th>
                    <td>${sessionScope.user.firstName}</td>
                </tr>
                <tr>
                    <th scope="col"><fmt:message key="user.label.last-name"/></th>
                    <td>${sessionScope.user.lastName}</td>
                </tr>
                <tr>
                    <th scope="col"><fmt:message key="user.label.email-address"/></th>
                    <td>${sessionScope.user.email}</td>
                </tr>
                <c:if test="${not (sessionScope.user.role eq Role.ADMIN)}">
                    <tr>
                        <th scope="col"><fmt:message key="user.label.total-fine"/></th>
                        <td class="text-danger">
                            <c:if test="${sessionScope.user.getTotalFine() gt 0}">
                                <c:out value="$${sessionScope.user.getTotalFine()}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="user.label.banned"/></th>
                        <td>
                            <c:if test="${sessionScope.user.banned}">
                                <fmt:message key="user.label.banned.yes"/>
                            </c:if>
                            <c:if test="${not sessionScope.user.banned}">
                                <fmt:message key="user.label.banned.no"/>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>

        </div>
    </div>

</main>

<%@ include file="footer.jspf" %>

</body>
</html>
