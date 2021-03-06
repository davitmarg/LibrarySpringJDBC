package com.example.demo;


import java.sql.Date;

public class BookRecord {
    private int id;
    private int user_id;
    private int book_id;
    private Date date;

    public BookRecord(int user_id, int book_id, Date date) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.date = date;
    }

    public BookRecord(int id, int user_id, int book_id, Date date) {
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
