package ru.photorex.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {

    List<Book> findAllByAuthors(Author author);

    List<Book> findAllByGenres(String genre);
}
