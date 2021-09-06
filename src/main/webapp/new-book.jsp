<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Add book</title>
    <link href="dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>

    <link href="css/new-book.css" rel="stylesheet">
</head>
<body class="text-center">
<main class="form-new-book">
    <form action="NewBookServlet" method="post">
        <%--        <img class="mb-4" src="img/logo.svg" width="64" height="64" alt="logo">--%>
        <h1 class="h3 mb-3 fw-normal">Add book</h1>

        <div class="form-floating">
            <input name="title" type="text" class="form-control" id="title" placeholder="Title">
            <label for="title">Title</label>
        </div>

        <div class="form-floating">
            <input name="author" type="text" class="form-control" id="author" placeholder="Author">
            <label for="author">Author</label>
        </div>

        <div class="form-floating">
            <input name="publisher" type="text" class="form-control" id="publisher" placeholder="Publisher">
            <label for="publisher">Publisher</label>
        </div>

        <div class="form-floating">
            <input name="year" type="number" min="0" max="9999" class="form-control" id="year" placeholder="Publication year">
            <label for="year">Publication year</label>
        </div>

        <div class="form-floating">
            <input name="amount" type="number" min="0" class="form-control" id="amount" placeholder="Amount">
            <label for="amount">Amount</label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">Add</button>
    </form>
</main>

</body>
</html>
