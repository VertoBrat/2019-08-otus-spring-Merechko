package ru.photorex.hw8.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.photorex.hw8.model.Book;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final MongoOperations mongoOperations;

    @Override
    public void removeCommentsFromArrayById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull("comments", query);
        mongoOperations.updateMulti(new Query(), update, Book.class);
    }
}
