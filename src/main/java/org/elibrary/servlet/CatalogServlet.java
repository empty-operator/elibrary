package org.elibrary.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elibrary.dao.BookDao;
import org.elibrary.entity.Book;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CatalogServlet", value = "/catalog")
public class CatalogServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(CatalogServlet.class);
    private static final List<String> allowed = Arrays.asList("title", "author", "publisher", "year");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String query = request.getParameter("q");
        String searchBy = request.getParameter("search-by");
        String sortBy = request.getParameter("sort-by");
        String page = request.getParameter("page");
        query = query == null ? "" : query;
        searchBy = allowed.contains(searchBy) ? searchBy : "title";
        sortBy = allowed.contains(sortBy) ? sortBy : "title";
        try {
            request.setAttribute("list_of_books", filterBooks(query, searchBy, sortBy, page));
            request.setAttribute("amount_of_books", countPages(query, searchBy));
            request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            LOG.error("Cannot get books", e);
            response.sendRedirect("error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    private static List<Book> filterBooks(String query, String searchBy, String sortBy, String page) throws SQLException, NamingException {
        int pageInt;
        try {
            pageInt = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            pageInt = 1;
        }
        return BookDao.getInstance().getAll(query, searchBy, sortBy, pageInt);
    }

    private static int countPages(String query, String searchBy) throws SQLException, NamingException {
        int countBooks = BookDao.getInstance().countBooks(query, searchBy);
        return countBooks % 8 == 0 ? countBooks / 8 : countBooks / 8 + 1;
    }

}
