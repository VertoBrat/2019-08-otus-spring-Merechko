package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.BookRepository;
import ru.photorex.hw5.repository.mapper.BookRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final BookRowMapper mapper;

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
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Map<Long, Genre> getGenreForBooks(List<Long> ids) {
        Map<Long, Genre> map = new HashMap<>();
        if (ids != null) {
            Map<String, Object> params = Collections.singletonMap("id", ids);
            namedParameterJdbcOperations.query("select * from books_genres where book_id in (:id)", params, rs -> {
                map.putIfAbsent(rs.getLong("book_id"), new Genre(rs.getString("name")));
            });
        } else {
            namedParameterJdbcOperations.query("select * from books_genres", rs -> {
                map.putIfAbsent(rs.getLong("book_id"), new Genre(rs.getString("name")));
            });
        }
        return map;
    }
}
