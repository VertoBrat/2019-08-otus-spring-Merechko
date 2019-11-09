package ru.photorex.apiserver.repository;

import com.mongodb.DBRef;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Author;
import ru.photorex.apiserver.model.Book;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private static final String COMMENTS_COLLECTION = "comments";
    private static final String GENRES_FIELD = "genres";
    private static final String AUTHORS_FIELD = "authors";

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public void removeCommentsFromArrayById(String id) {
        Query query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
        Update update = new Update().pull(COMMENTS_COLLECTION, query);
        mongoTemplate.updateMulti(new Query(), update, Book.class).subscribe();
    }

    @Override
    public void addCommentToArray(String commentId, String bookId) {
        Update update = new Update().addToSet(COMMENTS_COLLECTION, new DBRef(COMMENTS_COLLECTION, commentId));
        mongoTemplate.updateFirst(new Query(Criteria.where(Fields.UNDERSCORE_ID).is(bookId)), update, Book.class).subscribe();
    }

    @Override
    public Flux<Book> findAllFilteredPerGenre(String genreName) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(GENRES_FIELD).regex(genreName, "i"))
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Book.class);
    }

    @Override
    public Flux<Book> findAllFilteredPerAuthors(Author author) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where(AUTHORS_FIELD)
                        .elemMatch(Criteria.where("firstName").regex(author.getFirstName(), "i").and("lastName").regex(author.getLastName(), "i")))
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Book.class);
    }

    @Override
    public Flux<String> findAllGenres() {
        Aggregation aggregation = newAggregation(
                unwind(GENRES_FIELD),
                project().andExclude(Fields.UNDERSCORE_ID).andInclude(GENRES_FIELD)
        );

        Flux<GenreProjection> genres = mongoTemplate.aggregate(aggregation, Book.class, GenreProjection.class);
        return genres.map(GenreProjection::getGenres).distinct();
    }

    @Override
    public Flux<Author> findAllAuthors() {
        Aggregation aggregation = newAggregation(
                unwind(AUTHORS_FIELD),
                project().andExclude(Fields.UNDERSCORE_ID).andInclude(AUTHORS_FIELD),
                project().and(valueOfToArray(AUTHORS_FIELD)).as("authors_map"),
                project().and("authors_map.v").arrayElementAt(0).as("firstName").and("authors_map.v").arrayElementAt(1).as("lastName")
        );

        Flux<Author> authors = mongoTemplate.aggregate(aggregation, Book.class, Author.class);
        return authors.distinct();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class GenreProjection {
        String genres;
    }
}
