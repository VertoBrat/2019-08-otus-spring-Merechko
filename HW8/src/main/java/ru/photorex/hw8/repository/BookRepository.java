package ru.photorex.hw8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw8.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {
}
