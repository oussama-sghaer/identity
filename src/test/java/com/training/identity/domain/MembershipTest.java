package com.training.identity.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembershipTest {

    @Test
    public void should_create_membership_with_valid_subject_team_role_and_status(){
        var subject = UserId.random();
        var team = TeamId.random();
        var role = Role.MEMBER;
        var status = MembershipStatus.ACTIVE;
        var membership = new Membership(subject,team,role,status);
        assertEquals(subject, membership.subject());
        assertEquals(team, membership.team());
        assertEquals(role, membership.role());
        assertEquals(status, membership.status());
    }
    @Test
    public void should_create_new_membership_with_same_subject_team_role_when_changing_status(){
        var subject = UserId.random();
        var team = TeamId.random();
        var role = Role.MEMBER;
        var status = MembershipStatus.ACTIVE;
        var membership = new Membership(subject,team,role,status);
        var newStatus = MembershipStatus.REMOVED;
        var newMembership = membership.withStatus(newStatus);
        assertEquals(subject, newMembership.subject());
        assertEquals(team, newMembership.team());
        assertEquals(role, newMembership.role());
        assertEquals(newStatus, newMembership.status());
    }
    @Test
    public void should_reject_null_subject() {
        assertThrows(IllegalArgumentException.class, () -> new Membership(null, TeamId.random(), Role.MEMBER, MembershipStatus.ACTIVE));
    }
    @Test
    public void should_reject_null_team() {
        assertThrows(IllegalArgumentException.class, () -> new Membership(UserId.random(), null, Role.MEMBER, MembershipStatus.ACTIVE));
    }
    @Test
    public void should_reject_null_role() {
        assertThrows(IllegalArgumentException.class, () -> new Membership(UserId.random(), TeamId.random(), null, MembershipStatus.ACTIVE));
    }
    @Test
    public void should_reject_null_status() {
        assertThrows(IllegalArgumentException.class, () -> new Membership(UserId.random(), TeamId.random(), Role.MEMBER, null));
    }
    @Test
    public void should_return_false_permission_membership_with_removed_status_role_member(){
        var membership = new Membership(UserId.random(), TeamId.random(), Role.MEMBER, MembershipStatus.REMOVED);
        assertFalse(membership.hasPermission(Permission.CAN_ACCESS));
        assertFalse(membership.hasPermission(Permission.CAN_MANAGE));
    }
    @Test
    public void should_return_false_permission_membership_with_removed_status_role_admin(){
        var membership = new Membership(UserId.random(), TeamId.random(), Role.ADMIN, MembershipStatus.REMOVED);
        assertFalse(membership.hasPermission(Permission.CAN_ACCESS));
        assertFalse(membership.hasPermission(Permission.CAN_MANAGE));
    }
    @Test
    public void should_return_true_permission_membership_with_active_status_role_member(){
        var membership = new Membership(UserId.random(), TeamId.random(), Role.MEMBER, MembershipStatus.ACTIVE);
        assertTrue(membership.hasPermission(Permission.CAN_ACCESS));
        assertFalse(membership.hasPermission(Permission.CAN_MANAGE));
    }
    @Test
    public void should_return_true_permission_membership_with_active_status_role_admin(){
        var membership = new Membership(UserId.random(), TeamId.random(), Role.ADMIN, MembershipStatus.ACTIVE);
        assertTrue(membership.hasPermission(Permission.CAN_ACCESS));
        assertTrue(membership.hasPermission(Permission.CAN_MANAGE));
    }

}