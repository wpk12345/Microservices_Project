package com.company.groupprojectbookservice.dao;

import com.company.groupprojectbookservice.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJdbcImpl implements BookDao {
    private static final String CREATE_SQL = "insert into book (title, author) values (?, ?)";
    private static final String SELECT_ALL_SQL = "select * from book";
    private static final String SELECT_BY_BOOKID_SQL = "select * from book where book_id = ?";
    private static final String UPDATE_SQL = "update book set title = ?, author = ? where book_id = ?";
    private static final String DELETE_SQL = "delete from book where book_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public BookDaoJdbcImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Book mapRowToBook(ResultSet rs, int rowNum)  throws SQLException
    {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));

        return book;
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        jdbcTemplate.update(CREATE_SQL, book.getTitle(), book.getAuthor());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        book.setBookId(id);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_SQL, this::mapRowToBook);
    }

    @Override
    public Book getBookById(int bookId) {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_BY_BOOKID_SQL, this::mapRowToBook, bookId);
        }
        catch (EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(UPDATE_SQL,
                book.getTitle(),
                book.getAuthor(),
                book.getBookId());
    }

    @Override
    public void deleteBook(int bookId) {
        jdbcTemplate.update(DELETE_SQL, bookId);
    }
}
