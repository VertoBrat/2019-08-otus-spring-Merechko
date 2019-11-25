package ru.photorex.hw13.acl.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import ru.photorex.hw13.model.User;
import ru.photorex.hw13.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AclSupportImpl implements AclSupport {

    private static final Logger logger = LoggerFactory.getLogger(AclSupportImpl.class);
    private final MongoMutableAclService aclService;
    private final UserRepository userRepository;

    @Override
    public void deleteFromAcls(String type, String id, boolean children) {
        aclService.deleteAcl(new ObjectIdentityImpl(type, id), children);
    }

    @Override
    public void removePermissionFromUser(String type, String id, String principal, Permission... permissions) {
        this.removePermissions(type, id, new PrincipalSid(principal), permissions);
    }

    private void removePermissions(String type, String id, PrincipalSid principalSid, Permission... permissions) {
        AuditableAcl acl = get(type, id);
        int index = 0;
        for (AccessControlEntry entry : acl.getEntries()) {
            boolean deletedEntry = false;
            for (Permission permission : permissions) {
                if (entry.isGranting()
                        && entry.getSid().equals(principalSid)
                        && entry.getPermission().equals(permission)) {
                    acl.deleteAce(index);
                    deletedEntry = true;
                }
            }
            index = deletedEntry ? index : index + 1;
        }
        aclService.updateAcl(acl);
    }

    @Override
    public void grantPermissionsToUser(String type, String id, String userName, Permission... permissions) {
        this.grantPermissions(type, id, userName, new PrincipalSid(userName), permissions);
    }

    private void grantPermissions(String type, String id, String owner, Sid sid, Permission... permissions) {
        AuditableAcl acl = getOrCreate(type, id);
        Set<Integer> indices = new HashSet<>();

        for (Permission permission : permissions) {
            int index = acl.getEntries().size();
            boolean granting = true;
            acl.insertAce(index, permission, sid, granting);
            indices.add(index);
        }

        List<Sid> enabledSids = getAllEnabledSidsBesidesOwner(owner);
        for (Sid s : enabledSids) {
            int index = acl.getEntries().size();
            boolean granting = true;
            acl.insertAce(index, BasePermission.READ, s, granting);
            indices.add(index);
        }

        for (Integer index : indices) {
            acl.updateAuditing(index, true, true);
        }

        acl = (AuditableAcl) aclService.updateAcl(acl);
        logger.info("Updated acl is {}", acl);
    }

    private AuditableAcl getOrCreate(String type, String id) {
        AuditableAcl acl = get(type, id);
        if (acl == null) {
            acl = create(type, id);
        }
        return acl;
    }

    private AuditableAcl get(String type, String id) {
        try {
            return (AuditableAcl) aclService.readAclById(new ObjectIdentityImpl(type, id));
        } catch (NotFoundException exception) {
            return null;
        }
    }

    private AuditableAcl create(String type, String id) {
        return  (AuditableAcl) aclService.createAcl(new ObjectIdentityImpl(type, id));
    }

    private List<Sid> getAllEnabledSidsBesidesOwner(String owner) {
        List<User> enabledUsers = userRepository.findAllByEnabled(true);
        return enabledUsers.stream()
                .filter(u -> !u.getUsername().equals(owner))
                .map(u -> new PrincipalSid(u.getUsername()))
                .collect(Collectors.toList());
    }
}
