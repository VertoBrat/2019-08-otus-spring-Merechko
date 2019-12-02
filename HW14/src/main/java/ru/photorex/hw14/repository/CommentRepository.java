package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.CommentTo;

public interface CommentRepository extends JpaRepository<CommentTo, Long> {
}
