package ru.photorex.hw6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.photorex.hw6.exception.NoDataWithThisIdException;
import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpaImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book getById(Long id) {
        return em.createQuery("select b from Book b join fetch b.genre left join fetch b.author ba where b.id=:id", Book.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        return em.createQuery("select distinct b from Book b join fetch b.genre left join fetch b.author ba where ba.id=:id order by b.id", Book.class)
                .setParameter("id", author.getId()).getResultList();
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select distinct b from Book b join fetch b.genre order by b.id", Book.class).getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            if (em.find(Book.class, book.getId()) != null)
                return em.merge(book);
            else throw new NoDataWithThisIdException(book.getId());
        }
    }

    @Override
    public boolean delete(Long id) {
        return em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id).executeUpdate() != 0;
    }
}
