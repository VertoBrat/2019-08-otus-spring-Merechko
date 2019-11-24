package ru.photorex.hw13.acl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.photorex.hw13.acl.model.MongoAcl;
import ru.photorex.hw13.acl.model.MongoSid;
import ru.photorex.hw13.acl.model.ObjectPermission;
import ru.photorex.hw13.acl.repository.MongoAclRepository;

import java.util.List;

public class MongoMutableAclService extends MongoAclService implements MutableAclService {

    private static final Logger logger = LoggerFactory.getLogger(MongoMutableAclService.class);
    private AclCache aclCache;

    public MongoMutableAclService(MongoAclRepository aclRepository, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(aclRepository, lookupStrategy);
        this.aclCache = aclCache;
    }

    @Override
    public MutableAcl createAcl(ObjectIdentity objectIdentity) {
        List<MongoAcl> dbAcl =
                aclRepository.findByObjectIdAndClassName(objectIdentity.getIdentifier().toString(), objectIdentity.getType());
        if (!dbAcl.isEmpty()) {
            throw new AlreadyExistsException(objectIdentity + " already exists");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);

        MongoAcl mongoAcl = new MongoAcl(objectIdentity.getType(), objectIdentity.getIdentifier().toString(),
                new MongoSid(sid.getPrincipal()), null, true);
        aclRepository.save(mongoAcl);
        Acl acl = readAclById(objectIdentity);
        return (MutableAcl) acl;
    }

    @Override
    public void deleteAcl(ObjectIdentity objectIdentity, boolean b) {
        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (b) {
            if (children != null) {
                for (ObjectIdentity child : children) {
                    deleteAcl(child, true);
                }
            }
        } else if (!children.isEmpty()) {
            throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.size() + " children)");
        }

        Long numRemowed = aclRepository.deleteByObjectId(objectIdentity.getIdentifier().toString());
        if (numRemowed == null || numRemowed < 1) {
            logger.info("Some problems with deleting");
        }
        aclCache.evictFromCache(objectIdentity);
    }

    @Override
    public MutableAcl updateAcl(MutableAcl mutableAcl) {
        MongoAcl mongoAcl = aclRepository.findById(mutableAcl.getId().toString())
                .orElseThrow(() -> new NotFoundException("No entry " + mutableAcl.getId()));
        mongoAcl.getPermissions().clear();

        for (AccessControlEntry _ace : mutableAcl.getEntries()) {
            AccessControlEntryImpl ace = (AccessControlEntryImpl) _ace;
            MongoSid sid = null;
            if (ace.getSid() instanceof PrincipalSid) {
                PrincipalSid principal = (PrincipalSid) ace.getSid();
                sid = new MongoSid(principal.getPrincipal(), true);
            } else if (ace.getSid() instanceof GrantedAuthoritySid) {
                GrantedAuthoritySid grantedAuthority = (GrantedAuthoritySid) ace.getSid();
                sid = new MongoSid(grantedAuthority.getGrantedAuthority(), false);
            }
            ObjectPermission permission =
                    new ObjectPermission(ace.getId().toString(), sid, ace.getPermission().getMask(),
                            ace.isGranting(), ace.isAuditSuccess(), ace.isAuditFailure());
            mongoAcl.getPermissions().add(permission);
        }
        aclRepository.save(mongoAcl);
        clearCacheIncludingChildren(mutableAcl.getObjectIdentity());
        return (MutableAcl) readAclById(mutableAcl.getObjectIdentity());
    }

    private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (children != null) {
            for (ObjectIdentity child : children) {
                clearCacheIncludingChildren(child);
            }
        }
        aclCache.evictFromCache(objectIdentity);
    }
}
