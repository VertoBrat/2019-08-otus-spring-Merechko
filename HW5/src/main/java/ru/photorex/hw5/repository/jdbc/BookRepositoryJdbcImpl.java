package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.BookRepository;
import ru.photorex.hw5.repository.mapper.BookRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
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
        Book book = namedParameterJdbcOperations.queryForObject("select * from books where id= :id", params, mapper);
        Map<String, Object> paramsForGenre = Collections.singletonMap("id", book.getId());
        Genre genre = namedParameterJdbcOperations.queryForObject("select name from books_genres where book_id= :id", paramsForGenre, (resultSet, i) -> new Genre(resultSet.getString("name")));
        book.setGenre(genre);
        return book;
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        Map<String, Object> paramsForIds = Collections.singletonMap("id", author.getId());
        List<Long> bookIds =
                namedParameterJdbcOperations.query("select * from books_authors where author_id= :id",
                        paramsForIds, (rs, i) -> rs.getLong("book_id"));
        Map<Long, Genre> map = getGenreForBooks(bookIds);
        Map<String, Object> params = Collections.singletonMap("id", bookIds);
        List<Book> books = namedParameterJdbcOperations.query("select * from books where id in (:id)", params, mapper);
        books.forEach(b -> b.setGenre(map.get(b.getId())));
        return books;
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Genre> map = getGenreForBooks(null);
        List<Book> books = namedParameterJdbcOperations.query("select * from books", mapper);
        books.forEach(b -> b.setGenre(map.get(b.getId())));
        return books;
    }

    @Override
    public Book save(Book book) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle());
        if (book.getId() == null) {
            Number key = insert.executeAndReturnKey(map);
            book.setId(key.longValue());
            MapSqlParameterSource param = new MapSqlParameterSource()
                    .addValue("book_id", book.getId())
                    .addValue("genre", book.getGenre().getName());
            namedParameterJdbcOperations.update("insert into books_genres (book_id, genre) values (:book_id, :genre)", param);
            jdbcTemplate.batchUpdate("insert into books_authors (book_id, author_id) values(?, ?)",
                    book.getAuthor(), book.getAuthor().size(),
                    (ps, author) -> {
                        ps.setLong(1, book.getId());
                        ps.setLong(2, author.getId());
                    });
        } else {
            if (namedParameterJdbcOperations.update("update books set title=:title where id=:id", map)!=0) {
                jdbcTemplate.update("update books_genres set genre=? where book_id=?", book.getGenre().getName(),book.getId());
            } else return null;
        }

        return book;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books_genres where book_id= :id", params);
        namedParameterJdbcOperations.update("delete from books_authors where book_id= :id", params);
        return namedParameterJdbcOperations.update("delete from books where id= :id", params) != 0;
    }

    private Map<Long, Genre> getGenreForBooks(List<Long> ids) {
        Map<Long, Genre> map = new HashMap<>();
        if (ids != null) {
            Map<String, Object> params = Collections.singletonMap("id", ids);
            namedParameterJdbcOperations.query("select * from books_genres where book_id in (:id)", params, rs -> {
                map.putIfAbsent(rs.getLong("book_id"), new Genre(rs.getString("genre")));
            });
        } else {
            namedParameterJdbcOperations.query("select * from books_genres", rs -> {
                map.putIfAbsent(rs.getLong("book_id"), new Genre(rs.getString("genre")));
            });
        }
        return map;
    }
}
