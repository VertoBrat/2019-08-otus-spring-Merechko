package ru.photorex.hw6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.photorex.hw6.exception.NoDataWithThisIdException;
import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpaImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> getAllByBook(Long bookId) {
        return em.createQuery("select c from Comment c where c.book.id=:id order by c.dateTime desc", Comment.class)
                .setParameter("id", bookId).getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            Comment dbComment = em.find(Comment.class, comment.getId());
            if (dbComment != null) {
                dbComment.setText(comment.getText());
                dbComment.setDateTime(comment.getDateTime());
                return dbComment;
            } else throw new NoDataWithThisIdException(comment.getId());
        }
    }

    @Override
    public boolean delete(Long id) {
        return em.createQuery("delete from Comment c where c.id=:id")
                .setParameter("id", id).executeUpdate() != 0;
    }
}
