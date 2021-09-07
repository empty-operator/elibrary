package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UsersServlet", value = "/UsersServlet")
public class UsersServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("list_of_users", UserDao.getInstance().getAll());
            request.getRequestDispatcher("users.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 07.09.2021 error handling
            LOG.error("Cannot get users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

}
