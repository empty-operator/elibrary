package org.elibrary.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class Loan implements Serializable {

    private int id;
    private User user;
    private Book book;
    private Timestamp loanedAt;
    private Timestamp dueDate;
    private Timestamp returnedAt;
    private boolean rejected;

    private static final int FINE_AMOUNT_PER_DAY = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Timestamp getLoanedAt() {
        return loanedAt;
    }

    public void setLoanedAt(Timestamp loanedAt) {
        this.loanedAt = loanedAt;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Timestamp getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(Timestamp returnedAt) {
        this.returnedAt = returnedAt;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getFine() {
        if (dueDate == null || returnedAt != null) {
            return "";
        }
        long days = Duration.between(dueDate.toLocalDateTime(), LocalDateTime.now()).toDays();
        if (days > 0) {
            return "$" + days * FINE_AMOUNT_PER_DAY;
        }
        return "";
    }

}
