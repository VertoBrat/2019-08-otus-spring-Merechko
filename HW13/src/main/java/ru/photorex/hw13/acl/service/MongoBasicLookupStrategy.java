package ru.photorex.hw13.acl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.util.FieldUtils;
import ru.photorex.hw13.acl.model.MongoAcl;
import ru.photorex.hw13.acl.model.ObjectPermission;

import java.lang.reflect.Field;
import java.util.*;

public class MongoBasicLookupStrategy implements LookupStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MongoBasicLookupStrategy.class);
    private final AclAuthorizationStrategy aclAuthorizationStrategy;
    private PermissionFactory permissionFactory = new DefaultPermissionFactory();
    private final AclCache aclCache;
    private final PermissionGrantingStrategy grantingStrategy;
    private final MongoTemplate mongoTemplate;

    private int batchSize = 50;
    private final Field fieldAces = FieldUtils.getField(AclImpl.class, "aces");

    public MongoBasicLookupStrategy(AclAuthorizationStrategy aclAuthorizationStrategy, AclCache aclCache, PermissionGrantingStrategy grantingStrategy, MongoTemplate mongoTemplate) {
        this.aclAuthorizationStrategy = aclAuthorizationStrategy;
        this.aclCache = aclCache;
        this.grantingStrategy = grantingStrategy;
        this.mongoTemplate = mongoTemplate;
        fieldAces.setAccessible(true);
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> list, List<Sid> sids) {

        Map<ObjectIdentity, Acl> result = new HashMap<>();
        Set<ObjectIdentity> currentBatchToLoad = new HashSet<>();

        for (int i = 0; i < list.size(); i++) {
            final ObjectIdentity oid = list.get(i);
            boolean aclFound = false;

            // Check we don't already have this ACL in the results
            if (result.containsKey(oid)) {
                aclFound = true;
            }

            // Check cache for the present ACL entry
            if (!aclFound) {
                Acl acl = aclCache.getFromCache(oid);

                // Ensure any cached element supports all the requested SIDs
                // (they should always, as our base impl doesn't filter on SID)
                if (acl != null) {
                    if (acl.isSidLoaded(sids)) {
                        if (definesAccessPermissionsForSids(acl, sids)) {
                            result.put(acl.getObjectIdentity(), acl);
                            aclFound = true;
                        }
                    }
                    else {
                        throw new IllegalStateException(
                                "Error: SID-filtered element detected when implementation does not perform SID filtering "
                                        + "- have you added something to the cache manually?");
                    }
                }
            }

            // Load the ACL from the database
            if (!aclFound) {
                currentBatchToLoad.add(oid);
            }

            // Is it time to load from Mongo Collection the currentBatchToLoad?
            if ((currentBatchToLoad.size() == this.batchSize)
                    || ((i + 1) == list.size())) {
                if (!currentBatchToLoad.isEmpty()) {
                    Map<ObjectIdentity, Acl> loadedBatch = lookupObjectIdentities(
                            currentBatchToLoad, sids);

                    // Add loaded batch (all elements 100% initialized) to results
                    result.putAll(loadedBatch);

                    // Add the loaded batch to the cache

                    for (Acl loadedAcl : loadedBatch.values()) {
                        aclCache.putInCache((AclImpl) loadedAcl);
                    }

                    currentBatchToLoad.clear();
                }
            }
        }
        return result;
    }

    private Map<ObjectIdentity, Acl> lookupObjectIdentities(Collection<ObjectIdentity> currentBatchToLoad, List<Sid> sids) {
        Set<String> objectIds = new LinkedHashSet<>();
        Set<String> objectTypes = new LinkedHashSet<>();

        currentBatchToLoad.forEach(oi -> {
            objectIds.add(oi.getIdentifier().toString());
            objectTypes.add(oi.getType());
        });

        Criteria where = Criteria.where("objectId").in(objectIds).and("className").in(objectTypes);
        List<MongoAcl> mongoAcls = mongoTemplate.find(Query.query(where), MongoAcl.class);

        Map<ObjectIdentity, Acl> result = new HashMap<>();
        for (MongoAcl mongoAcl : new ArrayList<>(mongoAcls)) {
            Acl acl = null;
            try {
                acl = convertMongoAclToAcl(mongoAcl, mongoAcls);
            } catch (ClassNotFoundException ex) {
                logger.warn("Some troubles with classCast");
            }
            if (acl != null) {
                result.put(acl.getObjectIdentity(), acl);
            }
        }

        return result;
    }

    private Acl convertMongoAclToAcl(MongoAcl mongoAcl, List<MongoAcl> mongoAcls) throws ClassNotFoundException {
        //Seek parent ACL
        Acl parent = null;
        if (mongoAcl.getParentId() != null) {
            MongoAcl parentMongoAcl = mongoAcls.stream()
                    .filter(mongoAcl1 -> mongoAcl1.getId().equals(mongoAcl.getParentId()))
                    .findFirst().orElse(null);

            if (parentMongoAcl == null) {
                parentMongoAcl = mongoTemplate.findById(mongoAcl.getParentId(), MongoAcl.class);
            }

            if (parentMongoAcl != null) {
                if (!mongoAcls.contains(parentMongoAcl)) {
                    mongoAcls.add(parentMongoAcl);
                }
                Acl cashedParent = aclCache.getFromCache(new ObjectIdentityImpl(parentMongoAcl.getClassName(), parentMongoAcl.getObjectId()));
                if (cashedParent == null) {
                    parent = convertMongoAclToAcl(parentMongoAcl, mongoAcls);
                    aclCache.putInCache((MutableAcl) parent);
                } else {
                    parent = cashedParent;
                }
            }
        }
        //Configure ACL
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Class.forName(mongoAcl.getClassName()), mongoAcl.getObjectId());
        Sid owner;
        String ownerName = mongoAcl.getOwner().getName();
        if (mongoAcl.getOwner().isPrincipal()) {
            owner = new PrincipalSid(ownerName);
        } else {
            owner = new GrantedAuthoritySid(ownerName);
        }
        AclImpl acl = new AclImpl(objectIdentity, mongoAcl.getId(), aclAuthorizationStrategy, grantingStrategy,
                parent, null, mongoAcl.isInheritPermissions(), owner);
        for (ObjectPermission permission : mongoAcl.getPermissions()) {
            Sid sid;
            String sidName = permission.getSid().getName();
            if (permission.getSid().isPrincipal()) {
                sid = new PrincipalSid(sidName);
            } else {
                sid = new GrantedAuthoritySid(sidName);
            }
            Permission permissionAcl = permissionFactory.buildFromMask(permission.getMask());
            AccessControlEntryImpl ace = new AccessControlEntryImpl(permission.getId(), acl, sid,
                    permissionAcl, permission.isGranting(), permission.isAuditSuccess(), permission.isAuditFailure());
            List<AccessControlEntryImpl> aces = readAces(acl);
            aces.add(ace);
        }
        aclCache.putInCache(acl);

        return acl;
    }

    protected boolean definesAccessPermissionsForSids(Acl acl, List<Sid> sids) {
        // check whether the list of sids is a match-all list or if the owner is found within the list
        if (sids == null || sids.isEmpty() || sids.contains(acl.getOwner())) {
            return true;
        }
        // check the contained permissions for permissions granted to a certain user available in the provided list of sids
        if (hasPermissionsForSids(acl, sids)) {
            return true;
        }
        // check if a parent reference is available and inheritance is enabled
        if (acl.getParentAcl() != null && acl.isEntriesInheriting()) {
            if (definesAccessPermissionsForSids(acl.getParentAcl(), sids)) {
                return true;
            }

            return hasPermissionsForSids(acl.getParentAcl(), sids);
        }
        return false;
    }

    protected boolean hasPermissionsForSids(Acl acl, List<Sid> sids) {
        for (AccessControlEntry ace : acl.getEntries()) {
            if (sids.contains(ace.getSid())) {
                return true;
            }
        }
        return false;
    }

    private List<AccessControlEntryImpl> readAces(AclImpl acl) {
        try {
            @SuppressWarnings("unchecked")
            List<AccessControlEntryImpl> ret = (List<AccessControlEntryImpl>) fieldAces.get(acl);
            return ret;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not obtain AclImpl.aces field", e);
        }
    }
}
