package ru.photorex.hw13.acl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectPermission {

    @Id
    private String id;

    private MongoSid sid;

    private int mask;

    private boolean granting;

    private boolean auditSuccess;

    private boolean auditFailure;
}
