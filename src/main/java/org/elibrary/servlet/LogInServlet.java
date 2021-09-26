package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LogInServlet", value = "/LogInServlet")
public class LogInServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LogInServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            User user = UserDao.getInstance().getByEmail(request.getParameter("email"));
            if (user == null) {
                request.setAttribute("invalid-email", true);
                request.getRequestDispatcher("login").forward(request, response);
                return;
            }
            if (BCrypt.checkpw(request.getParameter("password"), user.getPassword())) {
                if (request.getParameter("remember-me") != null) {
                    request.getSession().setMaxInactiveInterval(60 * 60 * 24);
                }
                request.getSession().setAttribute("user", user);
                response.sendRedirect("catalog");
            } else {
                request.setAttribute("invalid-password", true);
                request.getRequestDispatcher("login").forward(request, response);
            }
        } catch (SQLException | NamingException e) {
            LOG.error("User login error");
            response.sendRedirect("error");
        }
    }

}
