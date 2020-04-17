package ru.photorex.hw6.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.model.Book;
import ru.photorex.hw6.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий на базе JPA для работы с книгами ")
@DataJpaTest
@Import({BookRepositoryJpaImpl.class})
@Sql(scripts = {"classpath:data-test.sql"})
public class BookRepositoryJpaImplTest {

    private static final Long BOOK_1_ID = 1L;
    private static final Long NEW_BOOK_ID = 4L;
    private static final int LIST_SIZE_2 = 2;
    private static final int LIST_SIZE_3 = 3;
    private static final String BOOK_TITLE_COLUMN = "title";
    private static final String BOOK_1_TITLE = "title_1";
    private static final String BOOK_2_TITLE = "title_2";

    @Autowired
    BookRepositoryJpaImpl repository;

    @Autowired
    TestEntityManager em;

    @DisplayName(" должен возвращать книгу по id из базы данных")
    @Test
    void shouldReturnBookById() {
        val actualBook = repository.getById(BOOK_1_ID);
        val expectedBook = em.find(Book.class, BOOK_1_ID);
        assertThat(actualBook.getId()).isEqualTo(expectedBook.getId());
        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
    }

    @DisplayName(" должен вернуть список книг одного автора")
    @Test
    void shouldReturnListOfBooksByAuthor() {
        val listOfBooks = repository.getByAuthor(new Author(BOOK_1_ID));
        assertThat(listOfBooks).hasSize(LIST_SIZE_2);
        assertThat(listOfBooks.get(0)).hasFieldOrPropertyWithValue(BOOK_TITLE_COLUMN, BOOK_1_TITLE);
        assertThat(listOfBooks.get(1)).hasFieldOrPropertyWithValue(BOOK_TITLE_COLUMN, BOOK_2_TITLE);
    }

    @DisplayName(" должен возвращать все книги из базы данных")
    @Test
    void shouldReturnAllBooks() {
        val books = repository.getAll();
        assertThat(books).hasSize(LIST_SIZE_3);
    }

    @DisplayName(" должен сохранить в базу данных книгу")
    @Test
    void shouldSaveNewBook() {
        val actualBook = repository.save(new Book(null, "title", new Genre(1L, null)));
        val expectedBook = em.find(Book.class, NEW_BOOK_ID);
        assertThat(actualBook.getId()).isEqualTo(expectedBook.getId());
        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
    }

    @DisplayName(" должен удалять книгу из базы данных")
    @Test
    void shouldDeleteBookById() {
        boolean deleted = repository.delete(BOOK_1_ID);
        assertTrue(deleted);
        assertThat(em.find(Book.class, BOOK_1_ID)).isNull();
    }
}
