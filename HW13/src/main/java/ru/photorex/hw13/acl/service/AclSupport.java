package ru.photorex.hw13.acl.service;

import org.springframework.security.acls.model.Permission;

public interface AclSupport {

    void deleteFromAcls(String type, String id, boolean children);

    void removePermissionFromUser(String type, String id, String principal, Permission... permissions);

    void grantPermissionsToUser(String type, String id, String userName, Permission...permissions);
}
