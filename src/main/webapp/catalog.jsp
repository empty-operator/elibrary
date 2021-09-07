<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Catalog</title>

    <link href="css/catalog.css" rel="stylesheet">
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

    <c:if test="${empty sessionScope.user}">
        <section class="py-5 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 col-md-8 mx-auto">
                    <h1 class="fw-light">eLibrary</h1>
                    <p class="lead text-muted">Something short and leading about the collection below—its contents, the
                        creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it
                        entirely.</p>
                    <p>
                        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-primary my-2">Sign up</a>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary my-2">Log in</a>
                    </p>
                </div>
            </div>
        </section>
    </c:if>

    <div class="py-5 bg-light">
        <div class="container">

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
                                <form class="form-loan d-flex align-items-center" action="NewLoanServlet" method="post">
                                    <input name="book_id" type="hidden" value="${book.id}">
                                    <c:if test="${book.amount eq 0}">
                                        <button type="submit" class="btn btn-primary disabled">Borrow</button>
                                        <small class="text-muted">Not available</small>
                                    </c:if>
                                    <c:if test="${not (book.amount eq 0)}">
                                        <button type="submit" class="btn btn-primary">Borrow</button>
                                        <small class="text-muted"><c:out value="${book.getAmountString()}"/></small>
                                    </c:if>
                                </form>
                            </div>
                        </div>
                    </div>

                </c:forEach>

            </div>
        </div>
    </div>

</main>

<%@ include file="footer.jspf"%>

<script src="dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
