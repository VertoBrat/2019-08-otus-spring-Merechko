package ru.photorex.hw13.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.photorex.hw13.acl.model.MongoAcl;
import ru.photorex.hw13.acl.model.MongoSid;
import ru.photorex.hw13.acl.model.ObjectPermission;
import ru.photorex.hw13.model.Book;
import ru.photorex.hw13.model.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "002")
public class initAclCollections {

    private static final String BOOK_CLASS_NAME = "ru.photorex.hw13.model.Book";
    private static final String COMMENT_CLASS_NAME = "ru.photorex.hw13.model.Comment";
    private static final MongoSid ADMIN = new MongoSid("admin");
    private static final MongoSid USER = new MongoSid("user");
    private static final MongoSid EDITOR = new MongoSid("ROLE_EDITOR", false);
    private List<ObjectPermission> permissions = new ArrayList<>();

    @ChangeSet(order = "001", id = "initPermissionCollection", author = "photorex", runAlways = true)
    public void initPermissionCollection(MongoTemplate template) {
        ObjectPermission p1 = template.save(new ObjectPermission(USER, 1, true, false, false));
        ObjectPermission p2 = template.save(new ObjectPermission(ADMIN, 1, true, false, false));
        ObjectPermission p3 = template.save(new ObjectPermission(ADMIN, 2, true, false, false));
        ObjectPermission p4 = template.save(new ObjectPermission(new MongoSid("dima"), 1, true, false, false));
        ObjectPermission p5 = template.save(new ObjectPermission(USER, 2, true, false, false));
        ObjectPermission p6 = template.save(new ObjectPermission(USER, 8, true, false, false));
        ObjectPermission p7 = template.save(new ObjectPermission(ADMIN, 8, true, false, false));
        ObjectPermission p8 = template.save(new ObjectPermission(EDITOR, 1, true, false, false));
        ObjectPermission p9 = template.save(new ObjectPermission(EDITOR, 2, true, false, false));
        ObjectPermission p10 = template.save(new ObjectPermission(EDITOR, 4, true, false, false));
        ObjectPermission p11 = template.save(new ObjectPermission(EDITOR, 8, true, false, false));
        fillPermissionList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
    }

    @ChangeSet(order = "002", id = "initAclCollectionWithBook", author = "photorex", runAlways = true)
    public void initAclCollectionWithBook(MongoTemplate template) {
        List<Book> books = template.findAll(Book.class);
        MongoAcl acl1 = new MongoAcl(BOOK_CLASS_NAME, books.get(0).getId(), EDITOR, null, true);
        MongoAcl acl2 = new MongoAcl(BOOK_CLASS_NAME, books.get(1).getId(), EDITOR, null, true);
        MongoAcl acl3 = new MongoAcl(BOOK_CLASS_NAME, books.get(2).getId(), EDITOR, null, true);
        acl1.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(2),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        acl2.getPermissions().addAll(Arrays.asList(permissions.get(1), permissions.get(2),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        acl3.getPermissions().addAll(Arrays.asList(permissions.get(1), permissions.get(3),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        template.save(acl1);
        template.save(acl2);
        template.save(acl3);
    }

    @ChangeSet(order = "003", id = "initAclCollectionWithComment", author = "photorex", runAlways = true)
    public void initAclCollectionWithComment(MongoTemplate template) {
        List<Comment> comments = template.findAll(Comment.class);
        MongoAcl acl1 = new MongoAcl(COMMENT_CLASS_NAME, comments.get(0).getId(), USER, null, true);
        MongoAcl acl2 = new MongoAcl(COMMENT_CLASS_NAME, comments.get(1).getId(), USER, null, true);
        MongoAcl acl3 = new MongoAcl(COMMENT_CLASS_NAME, comments.get(2).getId(), USER, null, true);
        MongoAcl acl4 = new MongoAcl(COMMENT_CLASS_NAME, comments.get(3).getId(), ADMIN, null, true);
        acl1.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(4), permissions.get(5),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        acl2.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(4), permissions.get(5),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        acl3.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(4), permissions.get(5),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        acl4.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(2), permissions.get(6),
                permissions.get(7), permissions.get(8), permissions.get(9), permissions.get(10)));
        template.save(acl1);
        template.save(acl2);
        template.save(acl3);
        template.save(acl4);
    }

    private void fillPermissionList(ObjectPermission...permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }
}
