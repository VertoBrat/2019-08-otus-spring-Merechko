package ru.photorex.hw8.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeLog(order = "001")
public class InitEmbeddedMongoDbWithCollections {

    private List<Comment> comments = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "001", id = "dropDataBase", author = "photorex", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initCommentsCollection", author = "photorex", runAlways = true)
    public void initComments(MongoTemplate template) {
        Comment comment1 = template.save(new Comment("Text#1", null));
        Comment comment2 = template.save(new Comment("Text#2", null));
        Comment comment3 = template.save(new Comment("Text#3", null));
        Comment comment4 = template.save(new Comment("Text#4", null));
        fillCommentList(comment1, comment2, comment3, comment4);
    }

    @ChangeSet(order = "003", id = "initBooksCollection", author = "photorex", runAlways = true)
    public void initBooks(MongoTemplate template) {
        Book book1 = template.save(new Book("Title#1", Set.of("Genre#1"), Set.of(new Author("FirstName#1", "LastName#1")), List.of(comments.get(0))));
        Book book2 = template.save(new Book("Title#2", Set.of("Genre#2"), Set.of(new Author("FirstName#2", "LastName#2"), new Author("FirstName#1", "LastName#1")), List.of(comments.get(1), comments.get(2))));
        Book book3 = template.save(new Book("Title#3", Set.of("Genre#3", "Genre#1"), Set.of(new Author("FirstName#3", "LastName#3"), new Author("FirstName#4", "LastName#4")), List.of(comments.get(3))));
        fillBookList(book1, book2, book3);
    }

    @ChangeSet(order = "004", id = "fillCommentsWithBook", author = "photorex", runAlways = true)
    public void fillCommentsWithBooks(MongoTemplate template) {
        template.updateFirst(Query.query(where("id").is(comments.get(0).getId())), new Update().set("book", books.get(0)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(1).getId())), new Update().set("book", books.get(1)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(2).getId())), new Update().set("book", books.get(1)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(3).getId())), new Update().set("book", books.get(2)),Comment.class);
    }

    private void fillCommentList(Comment...comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    private void fillBookList(Book...books) {
        this.books.addAll(Arrays.asList(books));
    }
}
