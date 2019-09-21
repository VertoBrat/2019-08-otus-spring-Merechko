package ru.photorex.hw5.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insert;
    private final GenreRowMapper mapper;

    public GenreRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, JdbcTemplate jdbcTemplate, GenreRowMapper mapper) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("genre").usingGeneratedKeyColumns("id");
        this.mapper = mapper;
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> map = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, name from genres where id= :id", map, mapper);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select id, name from genres", mapper);
    }
}
