<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <form class="needs-validation" action="SignUpServlet" method="post" novalidate>

        <h1 class="h3 mb-3 fw-normal"><fmt:message key="signup.label"/></h1>

        <div class="form-floating">
            <input name="first-name" type="text" class="form-control" id="first-name" placeholder="First name" minlength="1" maxlength="40" required>
            <label for="first-name"><fmt:message key="user.label.first-name"/></label>
        </div>

        <div class="form-floating">
            <input name="last-name" type="text" class="form-control" id="last-name" placeholder="Last name" minlength="1" maxlength="40" required>
            <label for="last-name"><fmt:message key="user.label.last-name"/></label>
        </div>

        <c:if test="${not requestScope['invalid-email']}">
            <div class="form-floating">
                <input name="email" type="email" class="form-control" id="email" placeholder="name@example.com"
                       maxlength="40" required>
                <label for="email"><fmt:message key="user.label.email-address"/></label>
            </div>
        </c:if>

        <c:if test="${requestScope['invalid-email']}">
            <div class="form-floating">
                <input name="email" type="email" class="form-control is-invalid" id="email" placeholder="name@example.com"
                       maxlength="40" required>
                <label for="email"><fmt:message key="user.label.email-address"/></label>
                <div class="invalid-feedback">
                    <fmt:message key="login.label.invalid-email-exist"/>
                </div>
            </div>
        </c:if>

        <div class="form-floating">
            <input name="password" type="password" class="form-control" id="password" placeholder="Password" required>
            <label for="password"><fmt:message key="user.label.password"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="signup.label"/></button>
    </form>
</main>

<script>
    (function () {
        'use strict'
        let forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>

</body>
</html>
