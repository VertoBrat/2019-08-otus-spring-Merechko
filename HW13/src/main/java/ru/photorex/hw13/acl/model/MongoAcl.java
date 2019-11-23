package ru.photorex.hw13.acl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "acl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoAcl {

    @Id
    private String id;

    private String className;

    private String objectId;

    private MongoSid owner;

    private String parentId;

    private boolean inheritPermissions;

    @DBRef
    private List<ObjectPermission> permissions = new ArrayList<>();

    public MongoAcl(String className, String objectId, MongoSid owner, String parentId, boolean inheritPermissions) {
        this.className = className;
        this.objectId = objectId;
        this.owner = owner;
        this.parentId = parentId;
        this.inheritPermissions = inheritPermissions;
    }
}
