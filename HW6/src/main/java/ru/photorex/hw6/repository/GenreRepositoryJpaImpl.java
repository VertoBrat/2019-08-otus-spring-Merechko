package ru.photorex.hw6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.photorex.hw6.exception.NoDataWithThisIdException;
import ru.photorex.hw6.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpaImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre getById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g order by g.id", Genre.class).getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            if (em.find(Genre.class, genre.getId()) != null)
                return em.merge(genre);
            else throw new NoDataWithThisIdException(genre.getId());
        }
    }

    @Override
    public boolean delete(Long id) {
        return em.createQuery("delete from Genre g where g.id= :id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }
}
