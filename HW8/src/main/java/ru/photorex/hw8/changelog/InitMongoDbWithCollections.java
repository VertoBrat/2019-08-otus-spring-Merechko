package ru.photorex.hw8.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.photorex.hw8.model.Author;
import ru.photorex.hw8.model.Book;
import ru.photorex.hw8.model.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@ChangeLog(order = "001")
public class InitMongoDbWithCollections {

    private List<Comment> comments = new ArrayList<>();

    @ChangeSet(order = "001", id = "dropDataBase", author = "photorex", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initCommentsCollection", author = "photorex", runAlways = true)
    public void initComments(MongoTemplate template) {
        Comment comment1 = template.save(new Comment("Text#1"));
        Comment comment2 = template.save(new Comment("Text#2"));
        Comment comment3 = template.save(new Comment("Text#3"));
        Comment comment4 = template.save(new Comment("Text#4"));
        fillCommentList(comment1, comment2, comment3, comment4);
    }

    @ChangeSet(order = "003", id = "initBooksCollection", author = "photorex", runAlways = true)
    public void initBooks(MongoTemplate template) {
        template.save(new Book("Effective_Java_3rd", Set.of("Story"), Set.of(new Author("Joshua", "Bloh")), List.of(comments.get(0))));
        template.save(new Book("Civilization of status", Set.of("Roman"), Set.of(new Author("Robert", "Shekli")), List.of(comments.get(1), comments.get(2))));
        template.save(new Book("Missing Certificate", Set.of("Comedy"), Set.of(new Author("Nikolay", "Gogol"), new Author("NoFirstName", "NoLastName")), List.of(comments.get(3))));
    }

    private void fillCommentList(Comment...comments) {
        this.comments.addAll(Arrays.asList(comments));
    }
}
