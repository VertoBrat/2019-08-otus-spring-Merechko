package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.AuthorTo;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorTo, Long> {

    Optional<AuthorTo> findByFirstNameAndLastName(String firstName, String lastName);
}
