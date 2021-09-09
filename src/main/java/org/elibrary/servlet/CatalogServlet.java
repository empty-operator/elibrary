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
            Map<Boolean, List<Book>> map = BookDao.getInstance().getAll().stream()
                                                  .collect(Collectors.partitioningBy(x -> x.getAmount() == 0));
            request.setAttribute("list_of_books", Stream.concat(map.get(false).stream().sorted(Comparator.comparing(Book::getTitle)),
                                                                map.get(true).stream().sorted(Comparator.comparing(Book::getTitle)))
                                                        .collect(Collectors.toList()));
            request.getRequestDispatcher("WEB-INF/jsp/catalog.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {
            // TODO: 25.08.2021 error handling
            LOG.error("Cannot get books", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

}
