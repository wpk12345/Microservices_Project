package com.company.groupprojectbookservice.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookViewModel {
    private int bookId;
    private String title;
    private String author;
    private List<Note> notes = new ArrayList<>();

    public BookViewModel() {
    }

    public BookViewModel(int bookId, String title, String author, List<Note> notes) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNotes(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note)
    {
        notes.remove(note);
    }

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
        BookViewModel that = (BookViewModel) o;
        return getBookId() == that.getBookId() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAuthor(), that.getAuthor()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getTitle(), getAuthor(), getNotes());
    }

    @Override
    public String toString() {
        return "BookViewModel{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                '}';
    }
}
