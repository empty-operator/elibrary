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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "CatalogServlet", value = "/catalog")
public class CatalogServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(CatalogServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setAttribute("list_of_books", filterBooks(request.getParameter("q"),
                                                request.getParameter("search-by"),
                                                request.getParameter("sort-by"),
                                                request.getParameter("page")));
            System.out.println(BookDao.getInstance().countBooks());
            request.setAttribute("amount_of_books", BookDao.getInstance().countBooks());
            request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 25.08.2021 error handling
            LOG.error("Cannot get books", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    private static List<Book> filterBooks(String query, String searchBy, String sortBy, String page) throws SQLException, NamingException {
        List<String> allowed = Arrays.asList("title", "author", "publisher", "year");
        if (query == null) {
            query = "";
        }
        if (!allowed.contains(searchBy)) {
            searchBy = "title";
        }
        if (!allowed.contains(sortBy)) {
            sortBy = "title";
        }
        List<Book> books;
        try {
            books = BookDao.getInstance().getAll(query, searchBy, sortBy, Integer.parseInt(page));
        } catch (NumberFormatException e) {
            books = BookDao.getInstance().getAll(query, searchBy, sortBy, 1);
        }
        return books;
    }

}
