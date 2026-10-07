package com.training.identity.repository;

import com.training.identity.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MembershipRepositoryTest {
    @Autowired
    MembershipRepository repository;

    @Test
    void getTeam() {
        TeamId id = new TeamId(UUID.fromString("5988051e-b091-4c40-bc2d-da2e80d2a890"));
        Optional<Team> team = repository.getTeam(id);
        assertTrue(team.isPresent());
        assertEquals(id, team.get().id());
        TeamId id2 = new TeamId(UUID.fromString("5988051e-b091-4c40-bc2d-da2e80d2a891"));
        Optional<Team> team2 = repository.getTeam(id2);
        assertFalse(team2.isPresent());
    }

    @Test
    void findBySubjectAndTeam() {
        UserId subject = new UserId(UUID.fromString("b3554525-d56c-44ea-8b47-d5d2d0438efb"));
        TeamId team = new TeamId(UUID.fromString("5988051e-b091-4c40-bc2d-da2e80d2a890"));
        Optional<Membership> membership = repository.findBySubjectAndTeam(subject, team);
        assertTrue(membership.isPresent());
        assertEquals(subject, membership.get().subject());
        assertEquals(team, membership.get().team());
        assertEquals(MembershipStatus.ACTIVE, membership.get().status());
        assertEquals(Role.ADMIN, membership.get().role());
        assertTrue(membership.get().hasPermission(Permission.CAN_MANAGE));
        assertTrue(membership.get().hasPermission(Permission.CAN_ACCESS));
        UserId subject2 = new UserId(UUID.fromString("dbba810b-aaba-484a-80e3-804664085c34"));
        TeamId team2 = new TeamId(UUID.fromString("c4d6a835-b10e-4d73-94cc-93f3f74dcf32"));
        Optional<Membership> membership2 = repository.findBySubjectAndTeam(subject2, team2);
        assertFalse(membership2.isPresent());
    }
}