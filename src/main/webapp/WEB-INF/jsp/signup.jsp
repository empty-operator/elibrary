<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="signup.label"/></title>

    <link href="css/catalog.css" rel="stylesheet">
    <link href="css/signup.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body class="text-center">

<main class="form-signup">

    <form action="SignUpServlet" method="post">

        <h1 class="h3 mb-3 fw-normal"><fmt:message key="signup.label"/></h1>

        <div class="form-floating">
            <input name="first-name" type="text" class="form-control" id="first-name" placeholder="First name">
            <label for="first-name"><fmt:message key="user.label.first-name"/></label>
        </div>

        <div class="form-floating">
            <input name="last-name" type="text" class="form-control" id="last-name" placeholder="Last name">
            <label for="last-name"><fmt:message key="user.label.last-name"/></label>
        </div>

        <div class="form-floating">
            <input name="email" type="email" class="form-control" id="email" placeholder="name@example.com">
            <label for="email"><fmt:message key="user.label.email-address"/></label>
        </div>

        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="password" placeholder="Password">
            <label for="password"><fmt:message key="user.label.password"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="signup.label"/></button>
    </form>
</main>

</body>
</html>
