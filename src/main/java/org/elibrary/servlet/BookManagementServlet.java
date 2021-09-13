package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.BookDao;
import org.elibrary.entity.Book;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BookManagementServlet", value = "/book-management")
public class BookManagementServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(BookManagementServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            BookDao bookDao = BookDao.getInstance();
            Book book = bookDao.get(Integer.parseInt(request.getParameter("book-id")));
            switch (action) {
                case "edit":
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("new-book").forward(request, response);
                    break;
                case "delete":
                    bookDao.delete(book.getId());
                    response.sendRedirect("catalog");
                    break;
            }
        } catch (SQLException | NamingException e) {
            // TODO: 13.09.2021 error handling
            LOG.error("Cannot update book");
        }
    }

}
