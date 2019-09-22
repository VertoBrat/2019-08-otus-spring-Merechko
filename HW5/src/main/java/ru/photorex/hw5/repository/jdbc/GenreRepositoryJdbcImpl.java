package ru.photorex.hw5.repository.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final GenreRowMapper mapper;

    public GenreRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, GenreRowMapper mapper) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.mapper = mapper;
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> map = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, name from genres where id= :id", map, mapper);
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = namedParameterJdbcOperations.query("select id, name from genres", mapper);
        return genres.stream().distinct().collect(Collectors.toList());

    }
}
