<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Sign up</title>

    <link href="css/signup.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body class="text-center">

<main class="form-signup">
    <form action="SignUpServlet" method="post">
        <%--        <img class="mb-4" src="img/logo.svg" width="64" height="64" alt="logo">--%>
        <h1 class="h3 mb-3 fw-normal">Sign up</h1>

        <div class="form-floating">
            <input name="first-name" type="text" class="form-control" id="first-name" placeholder="First name">
            <label for="first-name">First name</label>
        </div>

        <div class="form-floating">
            <input name="last-name" type="text" class="form-control" id="last-name" placeholder="Last name">
            <label for="last-name">Last name</label>
        </div>

        <div class="form-floating">
            <input name="email" type="email" class="form-control" id="email" placeholder="name@example.com">
            <label for="email">Email address</label>
        </div>

        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="password" placeholder="Password">
            <label for="password">Password</label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign up</button>
    </form>
</main>

</body>
</html>
