package ru.photorex.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostFilter;
import ru.photorex.hw13.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {
}
