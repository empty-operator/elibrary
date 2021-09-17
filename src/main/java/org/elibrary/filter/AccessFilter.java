package org.elibrary.filter;

import org.elibrary.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private static final List<String> UNAUTHORISED = Arrays.asList("login", "signup", "logout", "catalog", "LogInServlet",
            "NewBookServlet", "SignUpServlet", "new-loan");
    private static final List<String> READER = Arrays.asList("new-loan", "user-loans");
    private static final List<String> LIBRARIAN = Arrays.asList("LoanManagementServlet", "loans", "user-loans", "users");
    private static final List<String> ADMIN = Arrays.asList("book-management", "NewBookServlet", "new-book", "UserManagementServlet",
            "users");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String resource = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println(resource);
        if (!resource.endsWith(".css")) {
            User user = (User) httpRequest.getSession().getAttribute("user");
            if (user != null) {
                switch (user.getRole()) {
                    case READER:
                        if (!(READER.contains(resource) || UNAUTHORISED.contains(resource))) {
                            httpResponse.sendRedirect("catalog");
                            return;
                        }
                        break;
                    case LIBRARIAN:
                        if (!(LIBRARIAN.contains(resource) || UNAUTHORISED.contains(resource))) {
                            httpResponse.sendRedirect("catalog");
                            return;
                        }
                        break;
                    case ADMIN:
                        if (!(ADMIN.contains(resource) || UNAUTHORISED.contains(resource))) {
                            httpResponse.sendRedirect("catalog");
                            return;
                        }
                        break;
                }
            } else {
                if (!UNAUTHORISED.contains(resource)) {
                    httpResponse.sendRedirect("catalog");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

}
