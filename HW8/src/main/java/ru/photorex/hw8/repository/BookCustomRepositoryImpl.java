package ru.photorex.hw8.repository;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.photorex.hw8.model.Book;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private static final String COMMENTS_COLLECTION = "comments";

    private final MongoOperations mongoOperations;

    @Override
    public void removeCommentsFromArrayById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull(COMMENTS_COLLECTION, query);
        mongoOperations.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void addCommentToArray(String commentId, String bookId) {
        Update update = new Update().push(COMMENTS_COLLECTION, new DBRef(COMMENTS_COLLECTION, commentId));
        mongoOperations.updateFirst(new Query(Criteria.where("_id").is(bookId)), update, Book.class);
    }
}
