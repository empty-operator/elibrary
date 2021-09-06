package org.elibrary.dao;

import org.elibrary.entity.Loan;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {

    private static LoanDao instance;
    private static final String INSERT_LOAN = "INSERT INTO loan (user_id, book_id) VALUES (?, ?)";
    private static final String UPDATE_LOAN = "UPDATE loan SET user_id=(?), book_id=(?), loaned_at=(?), returned_at=(?), rejected=(?), due_date=(?) WHERE id=(?)";
    private static final String GET_LOANS = "SELECT * FROM loan";
    private static final String GET_USER_LOANS = "SELECT * FROM loan WHERE user_id=(?)";
    private static final String GET_LOAN_BY_ID = "SELECT user_id, book_id, loaned_at, returned_at, rejected, due_date FROM loan WHERE id=(?)";

    private LoanDao() {
    }

    public static synchronized LoanDao getInstance() {
        if (instance == null) {
            instance = new LoanDao();
        }
        return instance;
    }

    public Loan get(int id) throws SQLException, NamingException {
        Loan loan = new Loan();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LOAN_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    loan.setId(id);
                    loan.setUser(UserDao.getInstance().get(set.getInt(1)));
                    loan.setBook(BookDao.getInstance().get(set.getInt(2)));
                    loan.setLoanedAt(set.getTimestamp(3));
                    loan.setReturnedAt(set.getTimestamp(4));
                    loan.setRejected(set.getBoolean(5));
                    loan.setDueDate(set.getTimestamp(6));
                }
            }
        }
        return loan;
    }

    public List<Loan> getAll() throws SQLException, NamingException {
        ArrayList<Loan> loans = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(GET_LOANS)) {
            while (set.next()) {
                Loan loan = new Loan();
                loan.setId(set.getInt(1));
                loan.setUser(UserDao.getInstance().get(set.getInt(2)));
                loan.setBook(BookDao.getInstance().get(set.getInt(3)));
                loan.setLoanedAt(set.getTimestamp(4));
                loan.setReturnedAt(set.getTimestamp(5));
                loan.setRejected(set.getBoolean(6));
                loan.setDueDate(set.getTimestamp(7));
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> getAll(int userId) throws SQLException, NamingException {
        ArrayList<Loan> loans = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_LOANS)) {
            statement.setInt(1, userId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    Loan loan = new Loan();
                    loan.setId(set.getInt(1));
                    loan.setUser(UserDao.getInstance().get(set.getInt(2)));
                    loan.setBook(BookDao.getInstance().get(set.getInt(3)));
                    loan.setLoanedAt(set.getTimestamp(4));
                    loan.setReturnedAt(set.getTimestamp(5));
                    loan.setRejected(set.getBoolean(6));
                    loan.setDueDate(set.getTimestamp(7));
                    loans.add(loan);
                }
            }
        }
        return loans;
    }

    public void insert(Loan loan) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_LOAN, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, loan.getUser().getId());
            statement.setInt(2, loan.getBook().getId());
            if (statement.executeUpdate() > 0) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    if (set.next()) {
                        loan.setId(set.getInt(1));
                    }
                }
            }
        }
    }

    public void update(Loan loan) throws SQLException, NamingException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_LOAN)) {
            statement.setInt(1, loan.getUser().getId());
            statement.setInt(2, loan.getBook().getId());
            statement.setTimestamp(3, loan.getLoanedAt());
            statement.setTimestamp(4, loan.getReturnedAt());
            statement.setBoolean(5, loan.isRejected());
            statement.setTimestamp(6, loan.getDueDate());
            statement.setInt(7, loan.getId());
            statement.executeUpdate();
        }
    }

}
