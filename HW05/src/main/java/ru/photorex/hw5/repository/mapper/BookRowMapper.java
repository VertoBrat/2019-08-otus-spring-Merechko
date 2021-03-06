package ru.photorex.hw5.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setGenre(new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre")));
        return book;
    }
}
