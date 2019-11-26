package ru.photorex.hw13.test.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.photorex.hw13.model.Author;
import ru.photorex.hw13.model.Book;
import ru.photorex.hw13.model.Comment;
import ru.photorex.hw13.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeLog(order = "001")
public class InitEmbeddedMongoDbWithCollections {

    private List<Comment> comments = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @ChangeSet(order = "001", id = "dropDataBase", author = "photorex", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initCommentsCollection", author = "photorex", runAlways = true)
    public void initComments(MongoTemplate template) {
        Comment comment1 = template.save(new Comment("Text#1", null, null, LocalDateTime.now()));
        Comment comment2 = template.save(new Comment("Text#2", null, null, LocalDateTime.now()));
        Comment comment3 = template.save(new Comment("Text#3", null, null, LocalDateTime.now()));
        Comment comment4 = template.save(new Comment("Text#4", null, null, LocalDateTime.now()));
        fillCommentList(comment1, comment2, comment3, comment4);
    }

    @ChangeSet(order = "003", id = "initBooksCollection", author = "photorex", runAlways = true)
    public void initBooks(MongoTemplate template) {
        Book book1 = template.save(new Book("Title#1","Content#1", Set.of("Genre#1"), Set.of(new Author("FirstName#1", "LastName#1")), List.of(comments.get(0))));
        Book book2 = template.save(new Book("Title#2", "Content#2", Set.of("Genre#2"), Set.of(new Author("FirstName#2", "LastName#2"), new Author("FirstName#1", "LastName#1")), List.of(comments.get(1), comments.get(2))));
        Book book3 = template.save(new Book("Title#3", "Content#3", Set.of("Genre#3", "Genre#1"), Set.of(new Author("FirstName#3", "LastName#3"), new Author("FirstName#4", "LastName#4")), List.of(comments.get(3))));
        fillBookList(book1, book2, book3);
    }

    @ChangeSet(order = "004", id = "initUserCollection", author = "photorex", runAlways = true)
    public void initUserCollection(MongoTemplate template) {
        User user1 = template.save(new User("user", encoder.encode("user"), "Юзер Юзерович", Set.of(User.Role.ROLE_USER)));
        User user2 = template.save(new User("admin", encoder.encode("admin"), "Админ Админович", Set.of(User.Role.ROLE_ADMIN, User.Role.ROLE_USER)));
        User user3 = template.save(new User("editor", encoder.encode("editor"), "Всемогущий тип", Set.of(User.Role.ROLE_USER, User.Role.ROLE_ADMIN, User.Role.ROLE_EDITOR)));
        fillUserList(user1, user2, user3);
    }

    @ChangeSet(order = "005", id = "fillCommentsWithBook", author = "photorex", runAlways = true)
    public void fillCommentsWithBooks(MongoTemplate template) {
        template.updateFirst(Query.query(where("id").is(comments.get(0).getId())), new Update().set("book", books.get(0)).set("user", users.get(0)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(1).getId())), new Update().set("book", books.get(1)).set("user", users.get(0)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(2).getId())), new Update().set("book", books.get(1)).set("user", users.get(0)),Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(3).getId())), new Update().set("book", books.get(2)).set("user", users.get(1)),Comment.class);
    }

    private void fillCommentList(Comment...comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    private void fillBookList(Book...books) {
        this.books.addAll(Arrays.asList(books));
    }

    private void fillUserList(User...users) { this.users.addAll(Arrays.asList(users)); }
}
