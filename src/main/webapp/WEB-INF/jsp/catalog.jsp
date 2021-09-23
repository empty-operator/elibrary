<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="catalog.label"/></title>

    <link href="css/catalog.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>

<main>

    <%@ include file="header.jspf" %>

    <c:if test="${empty sessionScope.user}">
        <section class="py-5 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 col-md-8 mx-auto">
                    <h1 class="fw-light">eLibrary</h1>
                    <p class="lead text-muted"><fmt:message key="catalog.label.description-part-1"/><br><fmt:message key="catalog.label.description-part-2"/></p>
                    <p>
                        <a href="signup" class="btn btn-primary my-2"><fmt:message key="signup.label"/></a>
                        <a href="login" class="btn btn-secondary my-2"><fmt:message key="login.label"/></a>
                    </p>
                </div>
            </div>
        </section>
    </c:if>

    <div class="py-5 bg-light">
        <div class="container">

            <form class="row g-3 align-items-center" action="catalog" method="get">
                <div class="col-7">
                    <div class="form-floating">
                        <input id="q" value="${param.q}" name="q" type="text" class="form-control" placeholder="<fmt:message key="catalog.label.search-periods"/>">
                        <label for="q"><fmt:message key="catalog.label.search"/></label>
                    </div>
                </div>
                <div class="col-2">
                    <div class="form-floating">
                        <select id="search-by" class="form-select" name="search-by" aria-label="Search by">
                            <option value="title"><fmt:message key="catalog.book.label.title"/></option>
                            <option value="author"><fmt:message key="catalog.book.label.author"/></option>
                        </select>
                        <label for="search-by"><fmt:message key="catalog.label.search-by"/></label>
                    </div>
                </div>
                <div class="col-2">
                    <div class="form-floating">
                        <select id="sort-by" class="form-select" onchange="this.form.submit()" name="sort-by">
                            <option value="title"><fmt:message key="catalog.book.label.title"/></option>
                            <option value="author"><fmt:message key="catalog.book.label.author"/></option>
                            <option value="publisher"><fmt:message key="catalog.book.label.publisher"/></option>
                            <option value="year"><fmt:message key="catalog.book.label.year"/></option>
                        </select>
                        <label for="sort-by"><fmt:message key="catalog.label.sort-by"/></label>
                    </div>
                </div>
                <div class="col-1">
                    <button class="btn btn-lg btn-outline-secondary w-100" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                        </svg>
                    </button>
                </div>
                <input name="page" value="1" hidden>
            </form>

            <c:if test="${empty requestScope.list_of_books}">
                <h1><fmt:message key="catalog.label.did-not-find"/></h1>
                <p style="font-size: 24px"><fmt:message key="catalog.label.try-change-request"/></p>
            </c:if>

            <div class="row row-cols-4 g-3">

                <c:forEach items="${requestScope.list_of_books}" var="book">

                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title"><c:out value="${book.title}"/></h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><c:out value="${book.author}"/></li>
                                <li class="list-group-item"><c:out value="${book.publisher}"/></li>
                                <li class="list-group-item"><c:out value="${book.year}"/></li>
                            </ul>
                            <div class="card-footer">
                                <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                                    <form class="form-loan d-flex align-items-center" action="book-management" method="post">
                                        <input name="book-id" type="hidden" value="${book.id}">
                                        <button name="action" value="edit" type="submit" class="btn btn-primary"><fmt:message key="catalog.label.edit"/></button>
                                        <button name="action" value="delete" type="submit" class="btn btn-danger"><fmt:message key="catalog.label.remove"/></button>
                                    </form>
                                </c:if>
                                <c:if test="${not (sessionScope.user.role eq Role.ADMIN)}">
                                    <form class="form-loan d-flex align-items-center" action="new-loan" method="post">
                                        <input name="book-id" type="hidden" value="${book.id}">
                                        <c:if test="${book.amount eq 0}">
                                            <button type="submit" class="btn btn-primary disabled"><fmt:message key="catalog.label.borrow"/></button>
                                            <small class="text-muted"><fmt:message key="catalog.label.not-available"/></small>
                                        </c:if>
                                        <c:if test="${not (book.amount eq 0)}">
                                            <c:if test="${not sessionScope.user.banned}">
                                                <button type="submit" class="btn btn-primary"><fmt:message key="catalog.label.borrow"/></button>
                                            </c:if>
                                            <c:if test="${sessionScope.user.banned}">
                                                <button type="submit" class="btn btn-primary disabled"><fmt:message key="catalog.label.borrow"/></button>
                                            </c:if>
                                            <small class="text-muted"><c:out value="${book.getAmountString(cookie['lang'].value)}"/></small>
                                        </c:if>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </c:forEach>

            </div>

            <nav>
                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="${1}" end="${requestScope.amount_of_books}">
                        <li class="page-item">
                            <a class="page-link" href="catalog?q=${param.q}&search-by=${param['search-by']}&sort-by=${param['sort-by']}&page=${i}">
                                <c:out value="${i}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>

        </div>
    </div>

</main>

<%@ include file="footer.jspf" %>

<script>
    <c:if test="${not empty param['sort-by']}">
        document.getElementById('sort-by').value = '${param['sort-by']}';
    </c:if>
    <c:if test="${not empty param['search-by']}">
        document.getElementById('search-by').value = '${param['search-by']}';
    </c:if>
    <c:if test="${not empty param.page}">
        [...document.querySelectorAll("a")]
            .filter(a => a.textContent.includes('${param.page}'))
            .forEach(a => a.parentElement.classList.add('active'))
    </c:if>
</script>

</body>
</html>
