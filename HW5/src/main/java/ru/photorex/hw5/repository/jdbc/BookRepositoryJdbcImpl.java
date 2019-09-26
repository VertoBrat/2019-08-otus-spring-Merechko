package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.repository.BookRepository;
import ru.photorex.hw5.repository.mapper.BookRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ru.photorex.hw5.repository.jdbc.AuthorRepositoryJdbcImpl.AUTHOR_FIRST_NAME;
import static ru.photorex.hw5.repository.jdbc.AuthorRepositoryJdbcImpl.AUTHOR_LAST_NAME;

@RequiredArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepository {

    private static final String BOOK_TITLE = "title";
    private static final String BOOK_GENRE_ID = "genre_id";
    private static final String ID = "id";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final SimpleJdbcInsertOperations insert;
    private final BookRowMapper mapper;

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap(ID, id);
        Book book = namedParameterJdbcOperations.queryForObject("select b.id as id, b.title as title, g.id as genre_id, g.name as genre " +
                "from books b left join genres g on b.genre_id= g.id where b.id= :id", params, mapper);
        if (book != null) {
            List<Author> authors = namedParameterJdbcOperations.query("select a.id as id, a.first_name as firstName, a.last_name as lastName from authors a left join books_authors ba on ba.author_id=a.id where ba.book_id= :id", params,
                    (r, i) -> new Author(r.getLong(ID), r.getString(AUTHOR_FIRST_NAME), r.getString(AUTHOR_LAST_NAME)));
            book.setAuthor(authors);
        }
        return book;
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        Map<String, Object> paramsForIds = Collections.singletonMap(ID, author.getId());
        return namedParameterJdbcOperations.query("select b.id as id, b.title as title, g.id as genre_id, g.name as genre from books b " +
                "left join genres g on b.genre_id=g.id left join books_authors ba on b.id=ba.book_id where ba.author_id= :id", paramsForIds, mapper);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id as id, b.title as title,g.id as genre_id, g.name as genre from books b " +
                "left join genres g on b.genre_id=g.id order by b.id", mapper);
    }

    @Override
    public Book save(Book book) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue(ID, book.getId())
                .addValue(BOOK_TITLE, book.getTitle())
                .addValue(BOOK_GENRE_ID, book.getGenre().getId());
        if (book.getId() == null) {
            Number key = insert.executeAndReturnKey(map);
            book.setId(key.longValue());
            insertAuthorsOfBook(book);
        } else {
            if (namedParameterJdbcOperations.update("update books set title=:title, genre_id=:genre_id where id=:id", map) != 0) {
                if (!book.getAuthor().isEmpty()) {
                    insertAuthorsOfBook(book);
                }
            } else return null;
        }
        return book;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap(ID, id);
        return namedParameterJdbcOperations.update("delete from books where id= :id", params) != 0;
    }

    private void insertAuthorsOfBook(Book book) {
        namedParameterJdbcOperations.getJdbcOperations().batchUpdate("insert into books_authors (book_id, author_id) values(?, ?)",
                book.getAuthor(), book.getAuthor().size(),
                (ps, author) -> {
                    ps.setLong(1, book.getId());
                    ps.setLong(2, author.getId());
                });
    }
}
