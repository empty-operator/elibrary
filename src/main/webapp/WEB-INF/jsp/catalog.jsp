<%@ page contentType="text/html;charset=UTF-8" import="org.elibrary.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Catalog</title>

    <link href="css/catalog.css" rel="stylesheet">
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

    <c:if test="${empty sessionScope.user}">
        <section class="py-5 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 col-md-8 mx-auto">
                    <h1 class="fw-light">eLibrary</h1>
                    <p class="lead text-muted">Something short and leading about the collection below—its contents, the
                        creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it
                        entirely.</p>
                    <p>
                        <a href="signup" class="btn btn-primary my-2">Sign up</a>
                        <a href="login" class="btn btn-secondary my-2">Log in</a>
                    </p>
                </div>
            </div>
        </section>
    </c:if>

    <div class="py-5 bg-light">
        <div class="container">

            <form class="input-group" action="catalog" method="get">
                <input value="${param.q}" name="q" type="text" class="form-control w-50" placeholder="Search..."
                       aria-label="Search">
                <select id="search-by" class="form-select" name="search-by" aria-label="Search by">
                    <option value="" hidden>Search by</option>
                    <option value="title">Title</option>
                    <option value="author">Author</option>
                </select>
                <select id="sort-by" class="form-select" onchange="this.form.submit()" name="sort-by"
                        aria-label="Sort by">
                    <option value="" hidden>Sort by</option>
                    <option value="title">Title</option>
                    <option value="author">Author</option>
                    <option value="publisher">Publisher</option>
                    <option value="year">Year</option>
                </select>
                <button class="btn btn-outline-secondary" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                    </svg>
                </button>
            </form>

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
                                <form class="form-loan d-flex align-items-center" action="new-loan" method="post">
                                    <input name="book-id" type="hidden" value="${book.id}">
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

<%@ include file="footer.jspf" %>
<script>
    document.getElementById('search-by').value = '${param['search-by']}'
    document.getElementById('sort-by').value = '${param['sort-by']}'
</script>

</body>
</html>
