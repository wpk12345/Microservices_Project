package com.company.groupprojectnoteservice.dao;

import com.company.groupprojectnoteservice.models.Book;
import com.company.groupprojectnoteservice.models.Note;
import com.rabbitmq.client.AMQP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoJdbcTemplateImplTest {

    @Autowired
    protected NoteDao noteDao;


    @Before
    public void setUp() throws Exception {

        List<Note> noteList = noteDao.getAllNotes();
        for(Note n : noteList) {
            noteDao.deleteNote(n.getNoteId());
        }

    }

    @Test
    public void addGetDeleteNote() {

        Note note = new Note();
        note.setBookId(1);
        note.setNote("Test Note");

        note = noteDao.addNote(note);

        Note note1 = noteDao.getNote(note.getNoteId());

        assertEquals(note1, note);

        noteDao.deleteNote(note.getNoteId());

        note1 = noteDao.getNote(note.getNoteId());

        assertNull(note1);
    }

    @Test
    public void getAllNotes() {
        Note note = new Note();
        note.setBookId(1);
        note.setNote("Test Note");

        noteDao.addNote(note);

        note = new Note();
        note.setBookId(1);
        note.setNote("Test Note");

        noteDao.addNote(note);

        List<Note> noteList = noteDao.getAllNotes();

        assertEquals(noteList.size(), 2);
    }

    @Test
    public void updateNote() {
        Note note = new Note();
        note.setBookId(1);
        note.setNote("Test Note");

        note = noteDao.addNote(note);

        note.setBookId(2);
        note.setNote("Testing Note 2");

        noteDao.updateNote(note);

        Note note2 = noteDao.getNote(note.getNoteId());

        assertEquals(note2, note);
    }

    @Test
    public void getNotesByBook() {
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Title");
        book.setAuthor("Author");

        Note note = new Note();
        note.setBookId(book.getBookId());
        note.setNote("Test Note");

        note = noteDao.addNote(note);

        Note note1 = new Note();
        note1.setBookId(book.getBookId());
        note1.setNote("Testing Note 2");

        note1 = noteDao.addNote(note1);

        List<Note> nList = noteDao.getNotesByBook(book.getBookId());
        assertEquals(2, nList.size());
    }
}