package ru.photorex.hw5.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final GenreRowMapper mapper;

    @Override
    public Genre getById(Long id) {
        Map<String, Object> map = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from genre where id= :id", map, mapper);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from genre", mapper);
    }

    @Override
    public Genre save(Genre genre) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        if (genre.getId() == null) {
            namedParameterJdbcOperations.update("insert into genre values (:id, :name)", map);
        } else {
            if (namedParameterJdbcOperations.update("update genre set name=:name where id=:id", map)==0)
                return null;
        }
        return genre;
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where genre_id=:id", params);
        return namedParameterJdbcOperations.update("delete from genre where id=:id", params) !=0;
    }
}
