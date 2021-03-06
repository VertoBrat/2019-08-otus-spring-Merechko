package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw7.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
