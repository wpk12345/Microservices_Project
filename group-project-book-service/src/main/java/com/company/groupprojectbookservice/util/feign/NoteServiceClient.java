package com.company.groupprojectbookservice.util.feign;

import com.company.groupprojectbookservice.viewmodel.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "group-project-note-service")
public interface NoteServiceClient {

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note createNote(@RequestBody @Valid Note note);

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public List<Note> getAllNotes();

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable("id") int id);

    @RequestMapping(value = "/notes/book/{bookId}", method = RequestMethod.GET)
    public List<Note> getNoteByBookId(@PathVariable("bookId") int bookId);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNote(@PathVariable("id") int id, @RequestBody @Valid Note note);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable int id);

    @RequestMapping(value = "/notes/send", method = RequestMethod.GET)
    public Note getNoteVijaya(@RequestBody Note note);
}
