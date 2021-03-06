package ru.photorex.hw7.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;
import ru.photorex.hw7.model.Genre;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами")
@DataJpaTest
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryTest {

    private static final Long ENTITY_1_ID = 1L;
    private static final Long ENTITY_2_ID = 2L;
    private static final Long ENTITY_4_ID = 4L;
    private static final int FIRST_ELEMENT_COLLECTION = 0;
    private static final int SECOND_ELEMENT_COLLECTION = 1;
    private static final int LIST_SIZE_3 = 3;
    private static final int SET_SIZE_2 = 2;
    private static final String ENTITY_ID = "id";
    private static final String BOOK_TITLE = "title";
    private static final Author AUTHOR_1 = new Author(ENTITY_1_ID, "author_1_first_name", "author_1_last_name");
    private static final Author AUTHOR_2 = new Author(ENTITY_2_ID, "author_2_first_name", "author_2_last_name");

    @Autowired
    BookRepository repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен сохранять книгу в базу данных")
    @Test
    void shouldSaveBook() {
        Set<Author> authors = Set.of(new Author(ENTITY_1_ID), new Author(ENTITY_2_ID));
        Genre genre = new Genre(ENTITY_1_ID, null);
        Book actualBook = repository.save(new Book(null, BOOK_TITLE, genre, authors));
        Book expectedBook = em.find(Book.class, ENTITY_4_ID);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName(" должен получать лист с книгами одного автора")
    @Test
    void shouldReturnListOfBooksOneAuthor() {
        val books = repository.findBooksByAuthorOrderById(new Author(ENTITY_1_ID));
        assertThat(books).hasSize(SET_SIZE_2);
        assertThat(books.get(FIRST_ELEMENT_COLLECTION)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_1_ID);
        assertThat(books.get(SECOND_ELEMENT_COLLECTION)).hasFieldOrPropertyWithValue(ENTITY_ID, ENTITY_2_ID);
    }

    @DisplayName(" должен получать книгу по идентификатору с авторами")
    @Test
    void shouldReturnBookWithAuthorsById() {
        Optional<Book> book = repository.findWithAuthorsById(ENTITY_1_ID);
        assertThat(book).isPresent()
                .hasValueSatisfying(b -> {
                    assertThat(b.getAuthor()).hasSize(SET_SIZE_2);
                    assertThat(b.getAuthor()).containsExactly(AUTHOR_1, AUTHOR_2);
                });
    }

    @DisplayName(" должен получать все книги с авторами")
    @Test
    void shouldReturnListOfAllBooksWithAuthors() {
        val books = repository.findAll(Sort.by(ENTITY_ID));
        assertThat(books).hasSize(LIST_SIZE_3);
        assertThat(books.get(FIRST_ELEMENT_COLLECTION).getAuthor()).containsExactly(AUTHOR_1, AUTHOR_2);
    }
}
