package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LogInServlet", value = "/LogInServlet")
public class LogInServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LogInServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = UserDao.getInstance().getByEmail(request.getParameter("email"));
            if (BCrypt.checkpw(request.getParameter("password"), user.getPassword())) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("CatalogServlet");
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException | NamingException e) {
            // TODO: 26.08.2021 error handling
            LOG.error("User login error");
        }
    }

}
