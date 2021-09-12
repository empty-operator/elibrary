package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.NamingException;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        user.setFirstName(request.getParameter("first_name"));
        user.setLastName(request.getParameter("last_name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt()));
        try {
            UserDao.getInstance().insert(user);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("catalog");
        } catch (SQLException | NamingException e) {
            // TODO: 24.08.2021 error handling
            LOG.error("Cannot insert user" + e);
        }
    }

}
