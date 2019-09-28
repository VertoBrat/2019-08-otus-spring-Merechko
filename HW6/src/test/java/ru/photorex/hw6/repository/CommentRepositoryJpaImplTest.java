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

    private static final Long ID_1 = 1L;
    private static final Long ID_4 = 4L;
    private static final int LIST_SIZE = 2;
    private static final String COMMENT_TEXT = "comment";

    @Autowired
    CommentRepositoryJpaImpl repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен возвращать все комменты одной книги")
    @Test
    void shouldReturnAllCommentsByBook() {
        val comments = repository.getAllByBook(ID_1);
        assertThat(comments).hasSize(LIST_SIZE)
                .allMatch(c -> c.getText().contains(COMMENT_TEXT));
    }

    @DisplayName(" должен сохранять новый коммент в базе данных")
    @Test
    void shouldSaveNewComment() {
        Comment comment = new Comment(null, COMMENT_TEXT, new Book(ID_1), LocalDateTime.now());
        Comment dbComment = repository.save(comment);
        assertThat(dbComment.getId()).isNotNull().isEqualTo(ID_4);
    }

    @DisplayName(" должен удалять коммент из базы данных")
    @Test
    void shouldDeleteComment() {
        boolean deleted = repository.delete(ID_1);
        assertTrue(deleted);
        em.clear();
        assertThat(em.find(Comment.class, ID_1)).isNull();
    }
}
