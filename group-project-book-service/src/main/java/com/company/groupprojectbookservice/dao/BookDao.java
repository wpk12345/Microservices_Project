package com.company.groupprojectbookservice.dao;

import com.company.groupprojectbookservice.dto.Book;

import java.util.List;

public interface BookDao {

    Book createBook(Book book);

    List<Book> getAllBooks( );
    Book getBookById(int bookId );

    void updateBook(Book book);

    void deleteBook(int bookId);

}
