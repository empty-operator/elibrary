package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.UserDao;
import org.elibrary.entity.Role;
import org.elibrary.entity.User;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet(name = "UsersServlet", value = "/users")
public class UsersServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Predicate<User> userPredicate;
            if (((User) request.getSession().getAttribute("user")).getRole() == Role.LIBRARIAN) {
                userPredicate = x -> x.getRole() == Role.READER;
            } else {
                userPredicate = x -> x.getRole() == Role.READER || x.getRole() == Role.LIBRARIAN;
            }
            request.setAttribute("list_of_users", UserDao.getInstance().getAll().stream()
                                                         .filter(userPredicate)
                                                         .collect(Collectors.toList()));
            request.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 07.09.2021 error handling
            LOG.error("Cannot get users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

}
