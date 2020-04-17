package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private static final String GENRE_NAME = "name";
    private static final String ID = "id";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final SimpleJdbcInsertOperations insert;
    private final GenreRowMapper mapper;

    @Override
    public Genre getById(Long id) {
        Map<String, Object> map = Collections.singletonMap(ID, id);
        return namedParameterJdbcOperations.queryForObject("select id, name from genres where id= :id", map, mapper);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from genres", mapper);
    }

    @Override
    public Genre save(Genre genre) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue(ID, genre.getId())
                .addValue(GENRE_NAME, genre.getName());
        if (genre.getId() == null) {
            Number key = insert.executeAndReturnKey(map);
            genre.setId(key.longValue());
        } else {
            if (namedParameterJdbcOperations.update("update genre set name=:name where id=:id", map)==0)
                return null;
        }
        return genre;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap(ID, id);
        return namedParameterJdbcOperations.update("delete from genres where id=:id", params) !=0;
    }
}
