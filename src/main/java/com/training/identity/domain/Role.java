package com.training.identity.domain;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permission.CAN_ACCESS, Permission.CAN_MANAGE)),
    MEMBER(Set.of(Permission.CAN_ACCESS));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }
    public boolean hasPermission(Permission permission){
        return permissions.contains(permission);
    }
}
