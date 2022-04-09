package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.Role;
import org.elibrary.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignUpServlet", value = "/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(SignUpServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User();
        user.setFirstName(request.getParameter("first-name"));
        user.setLastName(request.getParameter("last-name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt()));
        user.setRole(Role.READER);
        try {
            UserDao userDao = UserDao.getInstance();
            if (userDao.getByEmail(user.getEmail()) == null) {
                userDao.insert(user);
                request.getSession().setAttribute("user", user);
                response.sendRedirect("catalog");
            } else {
                request.setAttribute("invalid-email", true);
                request.getRequestDispatcher("signup").forward(request, response);
            }
        } catch (SQLException | NamingException e) {
            LOG.error("Cannot insert user" + e);
            response.sendRedirect("error");
        }
    }

}
