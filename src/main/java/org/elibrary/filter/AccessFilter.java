package org.elibrary.filter;

import org.elibrary.entity.Role;
import org.elibrary.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class AccessFilter implements Filter {

    private static final Map<Role, List<String>> map = new EnumMap<>(Role.class);
    private static final List<String> UNAUTHORISED = Arrays.asList("login", "signup", "logout", "catalog", "LogInServlet",
            "SignUpServlet", "new-loan", "SetLocaleServlet");

    static {
        map.put(Role.READER, Arrays.asList("new-loan", "user-loans", "profile"));
        map.put(Role.LIBRARIAN, Arrays.asList("LoanManagementServlet", "loans", "user-loans", "users", "profile"));
        map.put(Role.ADMIN, Arrays.asList("book-management", "NewBookServlet", "new-book", "UserManagementServlet", "users", "profile"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String resource = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println(resource);
        if (!(resource.endsWith(".css") || resource.endsWith(".svg"))) {
            User user = (User) httpRequest.getSession().getAttribute("user");
            if (user != null) {
                if (!(map.get(user.getRole()).contains(resource) || UNAUTHORISED.contains(resource))) {
                    httpResponse.sendRedirect("catalog");
                    return;
                }
            } else if (!UNAUTHORISED.contains(resource)) {
                httpResponse.sendRedirect("catalog");
                return;
            }
        }
        chain.doFilter(request, response);
    }

}
