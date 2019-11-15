package ru.photorex.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw12.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {
}
