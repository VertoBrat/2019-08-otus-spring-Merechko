package ru.photorex.hw5.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.AuthorRepository;
import ru.photorex.hw5.repository.mapper.AuthorRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryJdbcImpl implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insert;
    private final AuthorRowMapper mapper;

    public AuthorRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, JdbcTemplate jdbcTemplate, AuthorRowMapper mapper) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("authors").usingGeneratedKeyColumns("id");
        this.mapper = mapper;
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from authors where id= :id", params, mapper);
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from authors", mapper);
    }

    @Override
    public Author save(Author author) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("firstName", author.getFirstName())
                .addValue("lastName", author.getLastName());
        if (author.getId() == null) {
            Number key = insert.executeAndReturnKey(map);
            author.setId(key.longValue());
        } else {
            if (namedParameterJdbcOperations.update("update authors set first_name=:firstName, last_name=:lastName " +
                    "where id=:id", map) == 0)
                return null;
        }
        return author;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books_authors where author_id= :id", params);
        return namedParameterJdbcOperations.update("delete from authors where id= :id", params) != 0;
    }
}
