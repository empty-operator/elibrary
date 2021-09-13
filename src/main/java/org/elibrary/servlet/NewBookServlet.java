package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.BookDao;
import org.elibrary.entity.Book;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NewBookServlet", value = "/NewBookServlet")
public class NewBookServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(NewBookServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("catalog");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setPublisher(request.getParameter("publisher"));
        book.setYear(Integer.parseInt(request.getParameter("year")));
        book.setAmount(Integer.parseInt(request.getParameter("amount")));
        String action = request.getParameter("action");
        try {
            BookDao bookDao = BookDao.getInstance();
            switch (action) {
                case "new":
                    bookDao.insert(book);
                    break;
                case "edit":
                    book.setId(Integer.parseInt(request.getParameter("book-id")));
                    bookDao.update(book);
                    break;
            }
        } catch (SQLException | NamingException e) {
            // TODO: 24.08.2021 error handling
            LOG.error("Cannot insert book" + e);
        }
        response.sendRedirect("catalog");
    }

}
