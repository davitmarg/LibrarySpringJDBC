package com.example.demo;
public class Book {
    private int id;
    private String title;
    private String authorName;

    public Book() {
    }

    public Book(String title, String authorName) {
        this.setTitle(title);
        this.setAuthorName(authorName);
    }

    public Book(int id, String title, String authorName) {
        this.setId(id);
        this.setTitle(title);
        this.setAuthorName(authorName);
    }

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
