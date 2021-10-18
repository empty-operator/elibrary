<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="login.label"/></title>

    <link href="css/catalog.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>

<body class="text-center">

<main class="form-login">

    <form class="needs-validation" action="LogInServlet" method="post" novalidate>
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.label"/></h1>

        <c:if test="${not requestScope['invalid-email']}">
            <div class="form-floating">
                <input name="email" type="email" class="form-control" id="email" placeholder="name@example.com">
                <label for="email"><fmt:message key="user.label.email-address"/></label>
            </div>
        </c:if>

        <c:if test="${requestScope['invalid-email']}">
            <div class="form-floating">
                <input name="email" type="email" class="form-control is-invalid" id="email" placeholder="name@example.com">
                <label for="email"><fmt:message key="user.label.email-address"/></label>
                <div class="invalid-feedback">
                    <fmt:message key="login.label.invalid-email-not-exist"/>
                </div>
            </div>
        </c:if>

        <c:if test="${not requestScope['invalid-password']}">
            <div class="form-floating">
                <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                <label for="password"><fmt:message key="user.label.password"/></label>
            </div>
        </c:if>

        <c:if test="${requestScope['invalid-password']}">
            <div class="form-floating">
                <input name="password" type="password" class="form-control is-invalid" id="password" placeholder="Password">
                <label for="password"><fmt:message key="user.label.password"/></label>
                <div class="invalid-feedback">
                    <fmt:message key="login.label.invalid-password"/>
                </div>
            </div>
        </c:if>

        <div class="checkbox mb-3">
            <label>
                <input name="remember-me" type="checkbox"> <fmt:message key="login.label.remember-me"/>
            </label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="login.label"/></button>
    </form>
</main>

</body>
</html>
