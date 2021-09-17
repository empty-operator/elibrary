package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.LoanDao;
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

@WebServlet(name = "UserLoansServlet", value = "/user-loans")
public class UserLoansServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(UserLoansServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int userId;
        if (user.getRole() == Role.READER) {
            userId = user.getId();
        } else {
            userId = Integer.parseInt(request.getParameter("user_id"));
        }
        try {
            request.setAttribute("list_of_loans", LoanDao.getInstance().getAll(userId));
            request.getRequestDispatcher("/WEB-INF/jsp/user-loans.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 25.08.2021 error handling
            LOG.error("Cannot get loans", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

}
