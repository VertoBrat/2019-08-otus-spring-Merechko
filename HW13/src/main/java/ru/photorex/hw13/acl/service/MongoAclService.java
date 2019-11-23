package ru.photorex.hw13.acl.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import ru.photorex.hw13.acl.model.MongoAcl;
import ru.photorex.hw13.acl.repository.MongoAclRepository;

import java.util.*;

@RequiredArgsConstructor
public class MongoAclService implements AclService {

    private static final Logger logger = LoggerFactory.getLogger(MongoAclService.class);

    protected final MongoAclRepository aclRepository;
    private final LookupStrategy lookupStrategy;

    @Override
    public List<ObjectIdentity> findChildren(ObjectIdentity objectIdentity) {
        List<MongoAcl> acls = aclRepository.findByObjectIdAndClassName(objectIdentity.getIdentifier().toString(), objectIdentity.getType());
        if (acls.isEmpty()) {
            return Collections.emptyList();
        }
        Set<MongoAcl> children = acls.stream()
                .map(m -> aclRepository.findByParentId(m.getId()))
                .collect(LinkedHashSet::new, LinkedHashSet::addAll, LinkedHashSet::addAll);
        List<ObjectIdentity> foundChildren = new ArrayList<>();
        for (MongoAcl child : children) {
            try {
                ObjectIdentity oId = new ObjectIdentityImpl(Class.forName(child.getClassName()), child.getObjectId());
                if (!foundChildren.contains(oId)) {
                    foundChildren.add(oId);
                }
            } catch (ClassNotFoundException cnfEx) {
                logger.error("Could not find class of domain object '{}' referenced by ACL {}",
                        child.getClassName(), child.getId());
            }
        }
        return foundChildren;
    }

    @Override
    public Acl readAclById(ObjectIdentity objectIdentity) {
        return readAclById(objectIdentity, null);
    }

    @Override
    public Acl readAclById(ObjectIdentity objectIdentity, List<Sid> list) {
        Map<ObjectIdentity, Acl> map = readAclsById(Collections.singletonList(objectIdentity), list);

        return map.get(objectIdentity);
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> list) {
        return readAclsById(list, null);
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> list, List<Sid> list1) {
        Map<ObjectIdentity, Acl> result = lookupStrategy.readAclsById(list, list1);
        for (ObjectIdentity oid : list) {
            if (!result.containsKey(oid)) {
                throw new NotFoundException(
                        "Unable to find ACL information for object identity '" + oid + "'");
            }
        }
        return result;
    }
}
