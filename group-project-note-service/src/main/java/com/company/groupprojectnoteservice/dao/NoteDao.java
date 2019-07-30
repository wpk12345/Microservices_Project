package com.company.groupprojectnoteservice.dao;

import com.company.groupprojectnoteservice.models.Note;

import java.util.List;

public interface NoteDao {

    Note addNote(Note note);
    Note getNote(int id);
    List<Note> getAllNotes();
    void updateNote(Note note);
    void deleteNote(int id);

    List<Note> getNotesByBook(int bookId);

}
