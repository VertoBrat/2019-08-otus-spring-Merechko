package ru.photorex.hw14.batch;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.photorex.hw14.model.mongo.Book;
import ru.photorex.hw14.model.mongo.Comment;
import ru.photorex.hw14.model.mongo.User;
import ru.photorex.hw14.model.sql.BookTo;
import ru.photorex.hw14.model.sql.CommentTo;
import ru.photorex.hw14.model.sql.UserTo;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    public static final String BOOKS = " books";
    public static final String BOOK_IDS ="bookIds";
    public static final String USERS = "users";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;

    @Bean
    public ItemReader<Book> bookMongoReader() {
        return new MongoItemReaderBuilder<Book>()
                .name("bookReader")
                .collection("books")
                .template(mongoTemplate)
                .sorts(new HashMap<>())
                .targetType(Book.class)
                .jsonQuery("{}")
                .build();
    }

    @Bean
    public ItemReader<User> userMongoReader() {
        return new MongoItemReaderBuilder<User>()
                .name("userReader")
                .collection("users")
                .template(mongoTemplate)
                .sorts(new HashMap<>())
                .targetType(User.class)
                .jsonQuery("{}")
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Comment> commentMongoReader(@Value("#{jobExecutionContext['bookIds']}")Set<String> ids) {
        Set<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toSet());
        return new MongoItemReaderBuilder<Comment>()
                .name("commentReader")
                .collection("comments")
                .template(mongoTemplate)
                .sorts(new HashMap<>())
                .parameterValues(ids)
                .targetType(Comment.class)
                .query(Query.query(Criteria.where("book.$id").in(objectIds)).limit(100))
                .pageSize(100)
                .build();
    }

    @Bean
    public Step bookStep(ItemReader<Book> reader,
                         ItemProcessor<Book, BookTo> processor,
                         ItemWriter<BookTo> writer) {
        return stepBuilderFactory
                .get("bookStep")
                .allowStartIfComplete(true)
                .<Book, BookTo>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(promotionBookListener())
                .build();
    }

    @Bean
    public Step userStep(ItemReader<User> reader,
                         ItemProcessor<User, UserTo> processor,
                         ItemWriter<UserTo> writer) {
        return stepBuilderFactory
                .get("userStep")
                .allowStartIfComplete(true)
                .<User, UserTo> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(promotionUserListener())
                .build();
    }

    @Bean
    public Step commentStep(ItemReader<Comment> reader,
                            ItemProcessor<Comment, CommentTo> processor,
                            ItemWriter<CommentTo> writer) {
        return stepBuilderFactory
                .get("commentStep")
                .allowStartIfComplete(true)
                .<Comment, CommentTo> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job firstJob(@Qualifier("bookStep") Step book,
                        @Qualifier("userStep") Step user,
                        @Qualifier("commentStep") Step comment) {
        return jobBuilderFactory
                .get("migrate")
                .incrementer(new RunIdIncrementer())
                .start(book)
                .next(user)
                .next(comment)
                .build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionBookListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] {BOOKS, BOOK_IDS});
        return listener;
    }

    @Bean
    public ExecutionContextPromotionListener promotionUserListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] {USERS});
        return listener;
    }

}
