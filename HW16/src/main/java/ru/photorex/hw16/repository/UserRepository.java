package ru.photorex.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw16.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName);
}
