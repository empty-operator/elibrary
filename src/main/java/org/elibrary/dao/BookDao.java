package org.elibrary.dao;

import org.elibrary.entity.Book;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements Dao<Book> {

    private static BookDao instance;
    private static final String INSERT_BOOK = "INSERT INTO book (title, author, publisher, year, amount) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE book SET title=(?), author=(?), publisher=(?), year=(?), amount=(?) WHERE id=(?)";
    private static final String DELETE_BOOK = "DELETE FROM book WHERE id=(?)";
    private static final String GET_BOOKS = "SELECT * FROM book WHERE %s ILIKE (?) ORDER BY %s LIMIT (?) OFFSET (?)";
    private static final String GET_BOOK_BY_ID = "SELECT title, author, publisher, year, amount FROM book WHERE id=(?)";
    private static final String COUNT_BOOKS = "SELECT COUNT(*) FROM book";
    private static final int itemsPerPage = 8;

    private BookDao() {
    }

    public static synchronized BookDao getInstance() {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

    @Override
    public Book get(int id) throws SQLException, NamingException {
        Book book = new Book();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BOOK_BY_ID)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                book.setId(id);
                book.setTitle(set.getString(1));
                book.setAuthor(set.getString(2));
                book.setPublisher(set.getString(3));
                book.setYear(set.getInt(4));
                book.setAmount(set.getInt(5));
            }
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<Book> getAll(String query, String searchBy, String sortBy, int page) throws SQLException, NamingException {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(String.format(GET_BOOKS, searchBy, sortBy))) {
            statement.setString(1, String.format("%%%s%%", query));
            statement.setInt(2, itemsPerPage);
            statement.setInt(3, itemsPerPage * (page - 1));
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    Book book = new Book();
                    book.setId(set.getInt(1));
                    book.setTitle(set.getString(2));
                    book.setAuthor(set.getString(3));
                    book.setPublisher(set.getString(4));
                    book.setYear(set.getInt(5));
                    book.setAmount(set.getInt(6));
                    books.add(book);
                }
            }
        }
        return books;
    }

    @Override
    public void insert(Book book) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getAmount());
            if (statement.executeUpdate() > 0) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    if (set.next()) {
                        book.setId(set.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(Book book) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getAmount());
            statement.setInt(6, book.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public int countBooks() throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(COUNT_BOOKS)) {
            set.next();
            return set.getInt(1);
        }
    }

}
