package ru.photorex.hw5.repository.jdbc;

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

@Repository
public class BookRepositoryJdbcImpl implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insert;
    private final BookRowMapper mapper;

    public BookRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, JdbcTemplate jdbcTemplate, BookRowMapper mapper) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("books").usingGeneratedKeyColumns("id");
        this.mapper = mapper;
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book = namedParameterJdbcOperations.queryForObject("select b.id as id, b.title as title, g.id as genre_id, g.name as genre " +
                "from books b left join genres g on b.id= g.book_id where b.id= :id", params, mapper);
        if (book != null) {
            List<Author> authors = namedParameterJdbcOperations.query("select * from authors a left join books_authors ba on ba.author_id=a.id where ba.book_id= :id", params,
                    (r, i) -> new Author(r.getLong("id"), r.getString("first_name"), r.getString("last_name")));
            book.setAuthor(authors);
        }
        return book;
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        Map<String, Object> paramsForIds = Collections.singletonMap("id", author.getId());
        return namedParameterJdbcOperations.query("select b.id as id, b.title as title, g.id as genre_id, g.name as genre from books b " +
                "left join genres g on b.id=g.book_id left join books_authors ba on b.id=ba.book_id where ba.author_id= :id", paramsForIds, mapper);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id as id, b.title as title,g.id as genre_id, g.name as genre from books b " +
                "left join genres g on b.id=g.book_id order by b.id", mapper);
    }

    @Override
    public Book save(Book book) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle());
        if (book.getId() == null) {
            Number key = insert.executeAndReturnKey(map);
            book.setId(key.longValue());
            MapSqlParameterSource mapGenres = new MapSqlParameterSource()
                    .addValue("book_id", book.getId())
                    .addValue("name", book.getGenre().getName());
            namedParameterJdbcOperations.update("insert into genres (book_id, name) values (:book_id, :name)", mapGenres);
            jdbcTemplate.batchUpdate("insert into books_authors (book_id, author_id) values(?, ?)",
                    book.getAuthor(), book.getAuthor().size(),
                    (ps, author) -> {
                        ps.setLong(1, book.getId());
                        ps.setLong(2, author.getId());
                    });
        } else {
            if (namedParameterJdbcOperations.update("update books set title=:title where id=:id", map) != 0) {
                if (book.getGenre() != null) {
                    MapSqlParameterSource mapGenres = new MapSqlParameterSource()
                            .addValue("id", book.getId())
                            .addValue("name", book.getGenre().getName());
                    namedParameterJdbcOperations.update("update genres set name=:name where book_id=:id", mapGenres);
                }
                if (!book.getAuthor().isEmpty()) {
                    jdbcTemplate.batchUpdate("insert into books_authors (book_id, author_id) values(?, ?)",
                            book.getAuthor(), book.getAuthor().size(),
                            (ps, author) -> {
                                ps.setLong(1, book.getId());
                                ps.setLong(2, author.getId());
                            });
                }

            } else return null;
        }
        return book;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books_authors where book_id= :id", params);
        namedParameterJdbcOperations.update("delete from genres where book_id= :id", params);
        return namedParameterJdbcOperations.update("delete from books where id= :id", params) != 0;
    }
}
