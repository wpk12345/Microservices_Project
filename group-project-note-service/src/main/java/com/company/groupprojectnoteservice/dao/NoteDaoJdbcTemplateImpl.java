package com.company.groupprojectnoteservice.dao;

import com.company.groupprojectnoteservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcTemplateImpl implements NoteDao{

    //Prepared statements
    private static final String INSERT_NOTE_SQL =
            "insert into note (book_id, note) values(?, ?)";

    private static final String SELECT_NOTE_SQL =
            "select * from note where note_id = ?";

    private static final String SELECT_ALL_NOTES_SQL =
            "select * from note";

    private static final String DELETE_NOTE =
            "delete from note where note_id = ?";

    private static final String UPDATE_NOTE =
            "update note set book_id = ?, note = ? where note_id = ?";

    private static final String SELECT_NOTES_BY_BOOK_SQL =
            "select * from note where book_id = ?";

    //JDBC template initializtion
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Note addNote(Note note) {

        jdbcTemplate.update(INSERT_NOTE_SQL,
                note.getBookId(),
                note.getNote());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        note.setNoteId(id);

        return note;
    }

    @Override
    public Note getNote(int id) {

        try {
            return jdbcTemplate.queryForObject(SELECT_NOTE_SQL, this::mapRowToNote, id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Note> getAllNotes() {
        return jdbcTemplate.query(SELECT_ALL_NOTES_SQL, this::mapRowToNote);
    }

    @Override
    public void updateNote(Note note) {

        jdbcTemplate.update(UPDATE_NOTE,
                note.getBookId(),
                note.getNote(),
                note.getNoteId());
    }

    @Override
    public void deleteNote(int id) {
        jdbcTemplate.update(DELETE_NOTE, id);
    }

    @Override
    public List<Note> getNotesByBook(int bookId) {
        return jdbcTemplate.query(SELECT_NOTES_BY_BOOK_SQL, this::mapRowToNote, bookId);
    }

    //Helper Methods(Row Mapper)
    private Note mapRowToNote(ResultSet rs, int rowNum) throws SQLException {
        Note note = new Note();

        note.setNoteId(rs.getInt("note_id"));
        note.setBookId(rs.getInt("book_id"));
        note.setNote(rs.getString("note"));

        return note;
    }
}
