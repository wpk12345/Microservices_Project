package com.company.groupprojectnoteservice.models;

import java.util.Objects;

public class Book {


    private int bookId;
    private String title;
    private String author;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book that = (Book) o;
        return getBookId() == that.getBookId() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getTitle(), getAuthor());
    }
}
