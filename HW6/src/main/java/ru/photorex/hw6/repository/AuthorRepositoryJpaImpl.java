package ru.photorex.hw6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.photorex.hw6.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpaImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author getById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
           return em.merge(author);
        }
    }

    @Override
    public boolean delete(Long id) {
        return em.createQuery("delete from Author a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }
}
