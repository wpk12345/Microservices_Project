package com.company.groupprojectbookservice.service;

import com.company.groupprojectbookservice.dao.BookDao;
import com.company.groupprojectbookservice.dto.Book;
import com.company.groupprojectbookservice.util.feign.NoteServiceClient;
import com.company.groupprojectbookservice.viewmodel.BookViewModel;
import com.company.groupprojectbookservice.viewmodel.Note;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    private NoteServiceClient noteClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE = "queue-note-exchange";
    public static final String ROUTING_KEY1 = "note.list.add.str";
    public static final String ROUTING_KEY2 = "note.list.obj.obj";
    public static final String ROUTING_KEY3 = "note.obj.#";


    public BookService(BookDao bookDao, RabbitTemplate rabbitTemplate)
    {
        this.bookDao = bookDao;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendingStr()
    {
        String str = "SENDING MESSAGE FOR TESTING";
        System.out.println(str);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY1, str);
        return str;
    }

    public Note sendingObj()
    {
        Note note = new Note(1,1,"HI");
        System.out.println(note);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY2, note);
        return note;
    }

    public List<Note> sendingList()
    {
        Note note1 = new Note(1,1,"HI1");
        Note note2 = new Note(2,1,"HI2");

        List<Note> noteList = new ArrayList<>(Arrays.asList(note1, note2));

        System.out.println(noteList);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY3, noteList);
        return noteList;
    }

    @Transactional
    public BookViewModel saveBook(BookViewModel bookViewModel)
    {
        Book book = new Book();
        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());

        book = bookDao.createBook(book);
        bookViewModel.setBookId(book.getBookId());

        List<Note> notes = bookViewModel.getNotes();

        notes.stream().
                forEach(n ->
                {
                    n.setBookId(bookViewModel.getBookId());
                    n = noteClient.createNote(n);
                });

        notes = noteClient.getNoteByBookId(bookViewModel.getBookId());
        bookViewModel.setNotes(notes);

        return bookViewModel;
    }

    public BookViewModel selectBookById(int id)
    {
        try {
            Book book = bookDao.getBookById(id);
            BookViewModel bvm = buildBookViewModel(book);
            return bvm;
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public List<BookViewModel> selectAllBooks()
    {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> bvmList = new ArrayList<>();

        for (Book book : bookList)
        {
            BookViewModel bvm = buildBookViewModel(book);
            bvmList.add(bvm);
        }
        return bvmList;
    }

    private BookViewModel buildBookViewModel(Book book)
    {
        List<Note> noteList = noteClient.getNoteByBookId(book.getBookId());

        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setBookId(book.getBookId());
        bookViewModel.setTitle(book.getTitle());
        bookViewModel.setAuthor(book.getAuthor());
        bookViewModel.setNotes(noteList);

        return bookViewModel;
    }

    @Transactional
    public void updateBook(BookViewModel bookViewModel)
    {
        Book book = new Book();
        book.setBookId(bookViewModel.getBookId());
        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());

        bookDao.updateBook(book);

        // We don't know if any track have been removed so delete all associated tracks
        // and then associate the tracks in the viewModel with the album
        List<Note> noteList = noteClient.getNoteByBookId(book.getBookId());
        noteList.stream()
                .forEach(note -> noteClient.deleteNote(note.getNoteId()));

        List<Note> notes = bookViewModel.getNotes();
        notes.stream()
                .forEach(n ->
                {
                    n.setBookId(bookViewModel.getBookId());
                    n = noteClient.createNote(n);
                });
    }

    @Transactional
    public void deleteBook(int id)
    {
        // Remove all associated Notes first
        List<Note> noteList = noteClient.getNoteByBookId(id);

        noteList.stream()
                .forEach(note -> noteClient.deleteNote(note.getNoteId()));

        // Remove Book
        bookDao.deleteBook(id);
    }
}
