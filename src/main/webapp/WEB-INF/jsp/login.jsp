<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="generator" content="Hugo 0.87.0">
    <title>Log in</title>

    <link href="dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">

    <style>
        @media (min-width: 768px) {
        }
    </style>

</head>
<body class="text-center">

<main class="form-login">
    <form action="LogInServlet" method="post">
<%--        <img class="mb-4" src="img/logo.svg" width="64" height="64" alt="logo">--%>
        <h1 class="h3 mb-3 fw-normal">Log in</h1>

        <div class="form-floating">
            <input name="email" type="email" class="form-control" id="email" placeholder="name@example.com">
            <label for="email">Email address</label>
        </div>

        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="password" placeholder="Password">
            <label for="password">Password</label>
        </div>

        <div class="checkbox mb-3">
            <label>
                <input name="remember-me" type="checkbox">Remember me
            </label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">Log in</button>
    </form>
</main>

</body>
</html>
