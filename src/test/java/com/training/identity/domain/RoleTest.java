package com.training.identity.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void admin_should_have_all_permissions() {
        var role = Role.ADMIN;
        assertTrue(role.hasPermission(Permission.CAN_ACCESS));
        assertTrue(role.hasPermission(Permission.CAN_MANAGE));
    }
    @Test
    void member_should_have_only_access_permission() {
        var role = Role.MEMBER ;
        assertTrue(role.hasPermission(Permission.CAN_ACCESS));
        assertFalse(role.hasPermission(Permission.CAN_MANAGE));
    }
}