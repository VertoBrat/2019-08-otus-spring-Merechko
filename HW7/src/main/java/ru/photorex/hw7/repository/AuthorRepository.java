package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw7.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
