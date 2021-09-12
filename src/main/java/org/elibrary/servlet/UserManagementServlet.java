package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.Role;
import org.elibrary.entity.User;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserManagementServlet", value = "/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(UserManagementServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            UserDao userDao = UserDao.getInstance();
            User user = userDao.get(Integer.parseInt(request.getParameter("user-id")));
            switch (action) {
                case "ban":
                    user.setBanned(true);
                    break;
                case "unban":
                    user.setBanned(false);
                    break;
                case "make-librarian":
                    user.setRole(Role.LIBRARIAN);
                    break;
                case "make-reader":
                    user.setRole(Role.READER);
                    break;
            }
            userDao.update(user);
            response.sendRedirect("users");
        } catch (SQLException | NamingException e) {
            // TODO: 08.09.2021 error handling
            LOG.error("Cannot update user", e);
        }
    }

}
