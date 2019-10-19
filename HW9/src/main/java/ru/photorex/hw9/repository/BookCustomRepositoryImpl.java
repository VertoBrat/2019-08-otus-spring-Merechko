package ru.photorex.hw9.repository;

import com.mongodb.DBRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.photorex.hw9.model.Author;
import ru.photorex.hw9.model.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private static final String COMMENTS_COLLECTION = "comments";
    private static final String GENRES_FIELD = "genres";
    private static final String AUTHORS_FIELD = "authors";

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
        mongoOperations.updateFirst(new Query(Criteria.where(Fields.UNDERSCORE_ID).is(bookId)), update, Book.class);
    }

    @Override
    public Set<String> findAllGenres() {
        Aggregation aggregation = newAggregation(
                unwind(GENRES_FIELD),
                project().andExclude(Fields.UNDERSCORE_ID).andInclude(GENRES_FIELD)
        );

        List<GenreProjection> genres = mongoOperations.aggregate(aggregation, Book.class, GenreProjection.class).getMappedResults();
        return genres.stream().map(GenreProjection::getGenres).collect(Collectors.toSet());
    }

    @Override
    public Set<Author> findAllAuthors() {
        Aggregation aggregation = newAggregation(
                unwind(AUTHORS_FIELD),
                project().andExclude(Fields.UNDERSCORE_ID).andInclude(AUTHORS_FIELD),
                project().and(valueOfToArray(AUTHORS_FIELD)).as("authors_map"),
                project().and("authors_map.v").arrayElementAt(0).as("firstName").and("authors_map.v").arrayElementAt(1).as("lastName")
        );

        List<Author> authors = mongoOperations.aggregate(aggregation, Book.class, Author.class).getMappedResults();
        return new HashSet<>(authors);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class GenreProjection {
        String genres;
    }
}
