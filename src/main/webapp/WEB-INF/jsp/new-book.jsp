<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>New book</title>

    <link href="css/new-book.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body class="text-center">

<main class="form-new-book">
    <form action="NewBookServlet" method="post">
        <%--        <img class="mb-4" src="img/logo.svg" width="64" height="64" alt="logo">--%>
        <h1 class="h3 mb-3 fw-normal">
            <c:if test="${empty requestScope.book}">Add book</c:if>
            <c:if test="${not empty requestScope.book}">Edit book</c:if>
        </h1>

        <div class="form-floating">
            <input name="title" value="${requestScope.book.title}" type="text" class="form-control" id="title" placeholder="Title">
            <label for="title">Title</label>
        </div>

        <div class="form-floating">
            <input name="author" value="${requestScope.book.author}" type="text" class="form-control" id="author"
                   placeholder="Author">
            <label for="author">Author</label>
        </div>

        <div class="form-floating">
            <input name="publisher" value="${requestScope.book.publisher}" type="text" class="form-control" id="publisher"
                   placeholder="Publisher">
            <label for="publisher">Publisher</label>
        </div>

        <div class="form-floating">
            <input name="year" value="${requestScope.book.year}" type="number" min="0" max="9999" class="form-control" id="year"
                   placeholder="Publication year">
            <label for="year">Publication year</label>
        </div>

        <div class="form-floating">
            <input name="amount" value="${requestScope.book.amount}" type="number" min="0" class="form-control" id="amount"
                   placeholder="Amount">
            <label for="amount">Amount</label>
        </div>

        <c:if test="${empty requestScope.book}">
            <button name="action" value="new" class="w-100 btn btn-lg btn-primary" type="submit">Add</button>
        </c:if>
        <c:if test="${not empty requestScope.book}">
            <input name="book-id" type="hidden" value="${requestScope.book.id}">
            <button name="action" value="edit" class="w-100 btn btn-lg btn-primary" type="submit">Edit</button>
        </c:if>

    </form>
</main>

</body>
</html>
