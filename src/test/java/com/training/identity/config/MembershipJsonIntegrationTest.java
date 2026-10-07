package com.training.identity.config;

import com.training.identity.domain.Membership;
import com.training.identity.domain.MembershipStatus;
import com.training.identity.domain.Role;
import com.training.identity.domain.TeamId;
import com.training.identity.domain.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MembershipJsonIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void context_should_provide_object_mapper() {
        assertNotNull(objectMapper);
    }

    @Test
    void should_round_trip_membership_with_user_id_and_team_id() {
        var membership = new Membership(
                UserId.random(),
                TeamId.random(),
                Role.ADMIN,
                MembershipStatus.ACTIVE
        );

        String json = objectMapper.writeValueAsString(membership);
        // bare-string shape proves the custom module ran, not Jackson's default record mapping
        assertTrue(json.contains("\"subject\":\"" + membership.subject().id() + "\""));
        assertTrue(json.contains("\"team\":\"" + membership.team().id() + "\""));

        Membership roundTripped = objectMapper.readValue(json, Membership.class);
        assertEquals(membership, roundTripped);
    }

    @Test
    void should_deserialize_real_fixture_membership_entry() {
        String json = """
                {
                  "subject": "b3554525-d56c-44ea-8b47-d5d2d0438efb",
                  "team": "5988051e-b091-4c40-bc2d-da2e80d2a890",
                  "role": "ADMIN",
                  "status": "ACTIVE"
                }
                """;

        Membership membership = objectMapper.readValue(json, Membership.class);

        assertEquals(new UserId(UUID.fromString("b3554525-d56c-44ea-8b47-d5d2d0438efb")), membership.subject());
        assertEquals(new TeamId(UUID.fromString("5988051e-b091-4c40-bc2d-da2e80d2a890")), membership.team());
        assertEquals(Role.ADMIN, membership.role());
        assertEquals(MembershipStatus.ACTIVE, membership.status());
    }
}
