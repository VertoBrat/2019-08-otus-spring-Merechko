package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.AuthorRepository;
import ru.photorex.hw5.repository.mapper.AuthorRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbcImpl implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorRowMapper mapper;

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
            namedParameterJdbcOperations.update("insert into authors (first_name, last_name) values (:firstName, :lastName)", map);
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
        deleteFromRelationship(id, params);
        return namedParameterJdbcOperations.update("delete from authors where id= :id", params) != 0;
    }

    private void deleteFromRelationship(Long id, Map<String, Object> params) {
        namedParameterJdbcOperations.update("delete from books_authors where author_id= :id", params);
    }
}
