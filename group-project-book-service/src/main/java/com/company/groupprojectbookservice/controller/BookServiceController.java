package com.company.groupprojectbookservice.controller;

import com.company.groupprojectbookservice.service.BookService;
import com.company.groupprojectbookservice.util.feign.NoteServiceClient;
import com.company.groupprojectbookservice.viewmodel.BookViewModel;
import com.company.groupprojectbookservice.viewmodel.Note;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class BookServiceController {

    BookService bookService;

    @Autowired
    public BookServiceController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookViewModel createBook(@RequestBody @Valid BookViewModel book) {

        return bookService.saveBook(book);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<BookViewModel> getAllBooks() {
        return bookService.selectAllBooks();
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public BookViewModel getBook(@PathVariable int bookId) {
        return bookService.selectBookById(bookId);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody @Valid BookViewModel bookViewModel, @PathVariable int bookId) {
        bookService.updateBook(bookViewModel);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
    }

}
