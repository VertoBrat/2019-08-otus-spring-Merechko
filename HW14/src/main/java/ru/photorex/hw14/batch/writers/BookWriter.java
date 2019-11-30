package ru.photorex.hw14.batch.writers;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw14.model.sql.AuthorTo;
import ru.photorex.hw14.model.sql.BookTo;
import ru.photorex.hw14.model.sql.GenreTo;
import ru.photorex.hw14.repository.AuthorRepository;
import ru.photorex.hw14.repository.BookRepository;
import ru.photorex.hw14.repository.GenreRepository;

import java.util.*;

@Component
public class BookWriter implements ItemWriter<BookTo> {

    private static final String BOOKS = "books";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private StepExecution stepExecution;

    public BookWriter(BookRepository bookRepository,
                      AuthorRepository authorRepository,
                      GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public void write(List<? extends BookTo> items) {
        if (!items.isEmpty()) {
            List<BookTo> listForSave = new ArrayList<>();
            for (BookTo to : items) {
                GenreTo dbGenreTo = genreRepository.findByName(to.getGenre().getName()).orElse(to.getGenre());
                to.setGenre(dbGenreTo);
                Set<AuthorTo> authorTos = new HashSet<>();
                for (AuthorTo authorTo : to.getAuthors()) {
                    AuthorTo dbAuthorTo = authorRepository
                            .findByFirstNameAndLastName(authorTo.getFirstName(), authorTo.getLastName())
                            .orElse(authorTo);
                    authorTos.add(dbAuthorTo);
                }
                to.setAuthors(authorTos);
                BookTo dbBookTo = bookRepository.save(to);
                listForSave.add(dbBookTo);
            }
            ExecutionContext stepContext = this.stepExecution.getExecutionContext();
            Map<String, Long> bookMap = new HashMap<>();
            if (stepContext.get(BOOKS) != null) {
                bookMap = (Map<String, Long>) stepContext.get(BOOKS);
            }
            fillBookMap(listForSave, bookMap);
            stepContext.put(BOOKS, bookMap);
        }
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    private void fillBookMap(List<BookTo> list, Map<String, Long> map) {
        for (BookTo b : list) {
            map.put(b.getIsbn(), b.getId());
        }
    }
}
