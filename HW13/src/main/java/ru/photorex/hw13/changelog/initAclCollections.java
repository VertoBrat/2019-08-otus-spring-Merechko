package ru.photorex.hw13.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.photorex.hw13.acl.model.MongoAcl;
import ru.photorex.hw13.acl.model.MongoSid;
import ru.photorex.hw13.acl.model.ObjectPermission;
import ru.photorex.hw13.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "002")
public class initAclCollections {

    private static final String BOOK_CLASS_NAME = "ru.photorex.hw13.model.Book";
    private static final MongoSid ADMIN = new MongoSid("admin");
    private List<ObjectPermission> permissions = new ArrayList<>();

    @ChangeSet(order = "001", id = "initPermissionCollection", author = "photorex", runAlways = true)
    public void initPermissionCollection(MongoTemplate template) {
        ObjectPermission p1 = template.save(new ObjectPermission(new MongoSid("user"), 1, true, false, false));
        ObjectPermission p2 = template.save(new ObjectPermission(ADMIN, 1, true, false, false));
        ObjectPermission p3 = template.save(new ObjectPermission(ADMIN, 2, true, false, false));
        ObjectPermission p4 = template.save(new ObjectPermission(new MongoSid("dima"), 1, true, false, false));
        fillPermissionList(p1, p2, p3, p4);
    }

    @ChangeSet(order = "002", id = "initAclCollection", author = "photorex", runAlways = true)
    public void initAclCollection(MongoTemplate template) {
        List<Book> books = template.findAll(Book.class);
        MongoAcl acl1 = new MongoAcl(BOOK_CLASS_NAME, books.get(0).getId(), ADMIN, null, true);
        MongoAcl acl2 = new MongoAcl(BOOK_CLASS_NAME, books.get(1).getId(), ADMIN, null, true);
        MongoAcl acl3 = new MongoAcl(BOOK_CLASS_NAME, books.get(2).getId(), ADMIN, null, true);
        acl1.getPermissions().addAll(Arrays.asList(permissions.get(0), permissions.get(1), permissions.get(2)));
        acl2.getPermissions().addAll(Arrays.asList(permissions.get(1), permissions.get(2)));
        acl3.getPermissions().addAll(Arrays.asList(permissions.get(1), permissions.get(2), permissions.get(3)));
        template.save(acl1);
        template.save(acl2);
        template.save(acl3);
    }

    private void fillPermissionList(ObjectPermission...permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }
}
