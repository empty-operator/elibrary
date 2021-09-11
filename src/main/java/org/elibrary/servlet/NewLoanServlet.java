package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.BookDao;
import org.elibrary.dao.LoanDao;
import org.elibrary.entity.Loan;
import org.elibrary.entity.User;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NewLoanServlet", value = "/new-loan")
public class NewLoanServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(NewLoanServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        Loan loan = new Loan();
        loan.setUser(user);
        try {
            loan.setBook(BookDao.getInstance().get(Integer.parseInt(request.getParameter("book-id"))));
            LoanDao.getInstance().insert(loan);
            response.sendRedirect("catalog");
        } catch (SQLException | NamingException e) {
            // TODO: 27.08.2021 error handling
            LOG.error("Cannot insert book loan" + e);
        }
    }

}
