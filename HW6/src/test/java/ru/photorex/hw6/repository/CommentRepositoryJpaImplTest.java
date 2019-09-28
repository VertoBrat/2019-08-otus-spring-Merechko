package ru.photorex.hw6.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Comment;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на основе JPA для работы с комментами ")
@DataJpaTest
@Import({CommentRepositoryJpaImpl.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class CommentRepositoryJpaImplTest {

    @Autowired
    CommentRepositoryJpaImpl repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен возвращать все комменты одной книги")
    @Test
    void shouldReturnAllCommentsByBook() {
        val comments = repository.getAllByBook(1L);
        assertThat(comments).hasSize(2)
                .allMatch(c -> c.getText().contains("comment"));
    }

    @DisplayName(" должен сохранять новый коммент в безе данных")
    @Test
    void shouldSaveNewComment() {
        Comment comment = new Comment(null, "comment", new Book(1L), LocalDateTime.now());
        Comment dbComment = repository.save(comment);
        assertThat(dbComment.getId()).isNotNull().isEqualTo(4L);
    }

    @DisplayName(" должен удалять коммент из базы данных")
    @Test
    void shouldDeleteComment() {
        boolean deleted = repository.delete(1L);
        assertTrue(deleted);
        em.clear();
        assertThat(em.find(Comment.class, 1L)).isNull();
    }
}
