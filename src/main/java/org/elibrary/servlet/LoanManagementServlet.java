package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.LoanDao;
import org.elibrary.entity.Loan;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "LoanManagementServlet", value = "/LoanManagementServlet")
public class LoanManagementServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LoanManagementServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        try {
            LoanDao loanDao = LoanDao.getInstance();
            Loan loan = loanDao.get(Integer.parseInt(request.getParameter("loan_id")));
            if ("approve".equals(action)) {
                loan.setLoanedAt(new Timestamp(System.currentTimeMillis()));
                loan.setDueDate(stringToTimestamp(request.getParameter("date")));
            } else if ("reject".equals(action)) {
                loan.setRejected(true);
            } else if ("return".equals(action)) {
                loan.setReturnedAt(new Timestamp(System.currentTimeMillis()));
            }
            loanDao.update(loan);
            response.sendRedirect("LoansServlet");
        } catch (SQLException | NamingException | ParseException e) {
            // TODO: 04.09.2021 error handling
            LOG.error("Cannot update loan", e);
        }
    }

    private Timestamp stringToTimestamp(String date) throws ParseException {
        return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
    }

}
