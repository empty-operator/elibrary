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
import java.util.Comparator;
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
            request.setAttribute("list_of_books", filterBooks(BookDao.getInstance().getAll(), request.getParameter("q"),
                    request.getParameter("search-by"), request.getParameter("sort-by")));
            request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 25.08.2021 error handling
            LOG.error("Cannot get books", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    private static List<Book> filterBooks(List<Book> books, String query, String searchBy, String sortBy) {
        Map<Boolean, List<Book>> map;
        if (query != null && searchBy != null) {
            switch (searchBy) {
                case "title":
                    map = books.stream()
                               .filter(x -> x.getTitle().contains(query))
                               .collect(Collectors.partitioningBy(x -> x.getAmount() == 0));
                    break;
                case "author":
                    map = books.stream()
                               .filter(x -> x.getAuthor().contains(query))
                               .collect(Collectors.partitioningBy(x -> x.getAmount() == 0));
                    break;
                default:
                    map = books.stream().collect(Collectors.partitioningBy(x -> x.getAmount() == 0));
            }
        } else {
            map = books.stream().collect(Collectors.partitioningBy(x -> x.getAmount() == 0));
        }
        Comparator<Book> comparator;
        if (sortBy != null) {
            switch (sortBy) {
                default:
                case "title":
                    comparator = Comparator.comparing(Book::getTitle);
                    break;
                case "author":
                    comparator = Comparator.comparing(Book::getAuthor);
                    break;
                case "publisher":
                    comparator = Comparator.comparing(Book::getPublisher);
                    break;
                case "year":
                    comparator = Comparator.comparing(Book::getYear).reversed();
                    break;
            }
        } else {
            comparator = Comparator.comparing(Book::getTitle);
        }
        return Stream.concat(map.get(false).stream().sorted(comparator), map.get(true).stream().sorted(comparator))
                     .collect(Collectors.toList());
    }

}
