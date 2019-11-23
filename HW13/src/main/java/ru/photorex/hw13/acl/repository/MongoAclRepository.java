package ru.photorex.hw13.acl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw13.acl.model.MongoAcl;

import java.util.List;

public interface MongoAclRepository extends MongoRepository<MongoAcl, String> {

    List<MongoAcl> findByObjectIdAndClassName(String objectId, String className);

    List<MongoAcl> findByParentId(String parentId);

    Long deleteByObjectId(String objectId);
}
