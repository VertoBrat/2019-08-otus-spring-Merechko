package ru.photorex.hw8.repository;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.photorex.hw8.model.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private static final String COMMENTS_COLLECTION = "comments";
    private static final String GENRES_FIELD = "genres";

    private final MongoOperations mongoOperations;

    @Override
    public void removeCommentsFromArrayById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull(COMMENTS_COLLECTION, query);
        mongoOperations.updateMulti(new Query(), update, Book.class);
    }

    @Override
    public void addCommentToArray(String commentId, String bookId) {
        Update update = new Update().addToSet(COMMENTS_COLLECTION, new DBRef(COMMENTS_COLLECTION, commentId));
        mongoOperations.updateFirst(new Query(Criteria.where("_id").is(bookId)), update, Book.class);
    }

    @Override
    public Set<String> findAllGenres() {
        Aggregation aggregation = newAggregation(
                unwind(GENRES_FIELD),
                project().andExclude("_id").andInclude(GENRES_FIELD)
        );

        List<Document> documents = mongoOperations.aggregate(aggregation, Book.class, Document.class).getMappedResults();
        return documents.stream().map(d -> (String)d.get(GENRES_FIELD)).collect(HashSet::new, HashSet::add, HashSet::addAll);
    }
}
