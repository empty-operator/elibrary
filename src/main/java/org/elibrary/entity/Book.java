package org.elibrary.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountString(String lang) {
        if (lang.equals("en")) {
            return amount + (amount > 1 ? " copies" : " copy");
        }
        if ((amount >= 10 && amount <= 20) || !(amount % 10 >= 1 && amount % 10 <= 4)) {
            return amount + " копій";
        } else if (amount % 10 == 1) {
            return amount + " копія";
        } else {
            return amount + " копії";
        }
    }

}
