package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.GenreTo;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreTo, Long> {

    Optional<GenreTo> findByName(String genreName);
}
