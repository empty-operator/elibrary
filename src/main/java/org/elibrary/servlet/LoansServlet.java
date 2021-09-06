package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.LoanDao;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoansServlet", value = "/LoansServlet")
public class LoansServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LoansServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("list_of_loans", LoanDao.getInstance().getAll());
        } catch (SQLException | NamingException e) {
            // TODO: 25.08.2021 error handling
            LOG.error("Cannot get loans", e);
        }
        request.getRequestDispatcher("loans.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

}
