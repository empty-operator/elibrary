package org.elibrary.dao;

import org.elibrary.entity.Book;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private static BookDao instance;
    private static final String INSERT_BOOK = "INSERT INTO book (title, author, publisher, year, amount) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BOOKS = "SELECT * FROM book";
    private static final String GET_BOOK_BY_ID = "SELECT title, author, publisher, year, amount FROM book WHERE id=(?)";

    private BookDao() {
    }

    public static synchronized BookDao getInstance() {
        if (instance == null) {
            instance = new BookDao();
        }
        return instance;
    }

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

    public List<Book> getAll() throws SQLException, NamingException {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(GET_BOOKS)) {
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
        return books;
    }

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

}
