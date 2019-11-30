package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.AuthorTo;

public interface AuthorRepository extends JpaRepository<AuthorTo, Long> {
}
