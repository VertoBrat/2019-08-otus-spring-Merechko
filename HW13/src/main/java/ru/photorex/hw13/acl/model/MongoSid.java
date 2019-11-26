package ru.photorex.hw13.acl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoSid {

    private String name;

    private boolean isPrincipal;

    public MongoSid(String name) {
        this.name = name;
        this.isPrincipal = true;
    }
}
