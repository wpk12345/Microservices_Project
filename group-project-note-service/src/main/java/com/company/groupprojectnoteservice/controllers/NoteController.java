package com.company.groupprojectnoteservice.controllers;

import com.company.groupprojectnoteservice.dao.NoteDao;
import com.company.groupprojectnoteservice.models.Note;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteDao noteDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody @Valid Note note) {
        return noteDao.addNote(note);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable("id") int id) {
        Note note = noteDao.getNote(id);
        if (note == null)
            throw new NotFoundException("Console could not be retrieved for id " + id);
        return note;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNotes() {
        List<Note> notes = noteDao.getAllNotes();

        return notes;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNote(@PathVariable("id") int id, @RequestBody @Valid Note note) {
        if (note.getNoteId() == 0)
            note.setNoteId(id);
        if (id != note.getNoteId()) {
            throw new IllegalArgumentException("Id on path must match the ID in the console object");
        }
        noteDao.updateNote(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable int id) {
        noteDao.deleteNote(id);
    }

    @GetMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getNoteByBookId(@PathVariable("bookId") int bookId ) {
        List<Note> note = noteDao.getNotesByBook(bookId);
        if (note != null && note.size() == 0)
            throw new NotFoundException("Notes(s) could not be retrieved for manufacturer " + note);
        return note;
    }
}
