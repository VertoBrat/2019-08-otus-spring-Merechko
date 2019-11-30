package ru.photorex.hw14.initmongoconfig.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.photorex.hw14.model.mongo.Author;
import ru.photorex.hw14.model.mongo.Book;
import ru.photorex.hw14.model.mongo.Comment;
import ru.photorex.hw14.model.mongo.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeLog(order = "001")
public class InitMongoDbWithCollections {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private List<Comment> comments = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();

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
        Comment comment5 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment6 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment7 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment8 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment9 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment10 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment11 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment12 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment13 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment14 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment15 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment16 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment17 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));
        Comment comment18 = template.save(new Comment("SomeText", null, null, LocalDateTime.now()));

        fillCommentList(comment1, comment2, comment3, comment4, comment5,
                comment6, comment7, comment8, comment9, comment10,
                comment11, comment12, comment13, comment14, comment15,
                comment16, comment17, comment18);
    }

    @ChangeSet(order = "003", id = "initBooksCollection", author = "photorex", runAlways = true)
    public void initBooks(MongoTemplate template) {
        String contentOne = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String contentTwo = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
        String contentThree = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.";
        Book book1 = template.save(new Book("Effective_Java_3rd", contentOne, "Story", "978-1-04-123456-7", Set.of(new Author("Joshua", "Bloh")), List.of(comments.get(0))));
        Book book2 = template.save(new Book("Civilization of status", contentTwo, "Roman", "978-4-23-123645-1", Set.of(new Author("Robert", "Shekli")), List.of(comments.get(1), comments.get(2))));
        Book book3 = template.save(new Book("Missing Certificate", contentThree, "Comedy", "978-9-12-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(3))));
        Book book4 = template.save(new Book("Title4", contentThree, "Comedy", "978-9-12-456231-5", Set.of(new Author("NoFirstName", "NoLastName")), List.of(comments.get(4))));
        Book book5 = template.save(new Book("Title5", contentThree, "Comedy", "978-9-12-456232-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(5))));
        Book book6 = template.save(new Book("Title6", contentThree, "Comedy", "978-9-12-456233-5", Set.of(new Author("NoFirstName", "NoLastName")), List.of(comments.get(6))));
        Book book7 = template.save(new Book("Title7", contentThree, "Comedy", "978-9-12-456234-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), null));
        Book book8 = template.save(new Book("Title8", contentThree, "Comedy", "978-9-12-456235-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), null));
        Book book9 = template.save(new Book("Title9", contentThree, "Comedy", "978-9-12-456236-5", Set.of(new Author("NoFirstName", "NoLastName")), List.of(comments.get(7))));
        Book book10 = template.save(new Book("Title10", contentThree, "Comedy", "978-9-12-456237-5", Set.of(new Author("NoFirstName", "NoLastName")), List.of(comments.get(8))));
        Book book11 = template.save(new Book("Title11", contentThree, "Comedy", "978-9-12-456238-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(9))));
        Book book12 = template.save(new Book("Title12", contentThree, "Comedy", "978-9-12-456239-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(10))));
        Book book13 = template.save(new Book("Title13", contentThree, "Comedy", "978-9-13-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(11))));
        Book book14 = template.save(new Book("Title14", contentThree, "Comedy", "978-9-14-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(12))));
        Book book15 = template.save(new Book("Title15", contentThree, "Comedy", "978-9-15-456231-5", Set.of(new Author("NoFirstName", "NoLastName")), List.of(comments.get(13))));
        Book book16 = template.save(new Book("Title16", contentThree, "Comedy", "978-9-16-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(14))));
        Book book17 = template.save(new Book("Title17", contentThree, "Comedy", "978-9-17-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(15))));
        Book book18 = template.save(new Book("Title18", contentThree, "Comedy", "978-9-18-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(16))));
        Book book19 = template.save(new Book("Title19", contentThree, "Comedy", "978-9-19-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(17))));
        Book book20 = template.save(new Book("Title20", contentThree, "Comedy", "978-9-20-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), null));
        Book book21 = template.save(new Book("Title21", contentThree, "Comedy", "978-9-21-456231-5", Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), null));

        fillBookList(book1, book2, book3, book4, book5, book6, book7,
                book8, book9, book10, book11, book12, book13, book14,
                book15, book16, book17, book18, book19, book20, book21);
    }

    @ChangeSet(order = "004", id = "initUserCollection", author = "photorex", runAlways = true)
    public void initUserCollection(MongoTemplate template) {
        User user1 = template.save(new User("user", encoder.encode("user"), "Юзер Юзерович", Set.of(User.Role.ROLE_USER)));
        User user2 = template.save(new User("admin", encoder.encode("admin"), "Админ Админович", Set.of(User.Role.ROLE_ADMIN, User.Role.ROLE_USER)));
        fillUserList(user1, user2);
    }

    @ChangeSet(order = "005", id = "fillCommentsWithBook", author = "photorex", runAlways = true)
    public void fillCommentsWithBooks(MongoTemplate template) {
        template.updateFirst(Query.query(where("id").is(comments.get(0).getId())), new Update().set("book", books.get(0)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(1).getId())), new Update().set("book", books.get(1)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(2).getId())), new Update().set("book", books.get(1)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(3).getId())), new Update().set("book", books.get(2)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(4).getId())), new Update().set("book", books.get(3)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(5).getId())), new Update().set("book", books.get(4)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(6).getId())), new Update().set("book", books.get(5)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(7).getId())), new Update().set("book", books.get(8)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(8).getId())), new Update().set("book", books.get(9)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(9).getId())), new Update().set("book", books.get(10)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(10).getId())), new Update().set("book", books.get(11)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(11).getId())), new Update().set("book", books.get(12)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(12).getId())), new Update().set("book", books.get(13)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(13).getId())), new Update().set("book", books.get(14)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(14).getId())), new Update().set("book", books.get(15)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(15).getId())), new Update().set("book", books.get(16)).set("user", users.get(0)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(16).getId())), new Update().set("book", books.get(17)).set("user", users.get(1)), Comment.class);
        template.updateFirst(Query.query(where("id").is(comments.get(17).getId())), new Update().set("book", books.get(18)).set("user", users.get(1)), Comment.class);
    }

    private void fillCommentList(Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }

    private void fillBookList(Book... books) {
        this.books.addAll(Arrays.asList(books));
    }

    private void fillUserList(User... users) {
        this.users.addAll(Arrays.asList(users));
    }
}
