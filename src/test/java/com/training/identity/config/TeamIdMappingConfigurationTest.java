package com.training.identity.config;

import com.training.identity.domain.TeamId;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TeamIdMappingConfigurationTest {

    private ObjectMapper newMapperWithModule() {
        return JsonMapper.builder()
                .addModule(new TeamIdMappingConfiguration().teamIdJacksonModule())
                .build();
    }

    @Test
    void should_serialize_team_id_as_bare_uuid_string() {
        var mapper = newMapperWithModule();
        var teamId = new TeamId(UUID.fromString("272123d6-1c52-4d38-8c56-cdc332941bd4"));

        String json = mapper.writeValueAsString(teamId);

        assertEquals("\"272123d6-1c52-4d38-8c56-cdc332941bd4\"", json);
    }

    @Test
    void should_deserialize_bare_uuid_string_into_team_id() {
        var mapper = newMapperWithModule();
        var teamId = new TeamId(UUID.fromString("272123d6-1c52-4d38-8c56-cdc332941bd4"));

        assertEquals(teamId, mapper.readValue("\"272123d6-1c52-4d38-8c56-cdc332941bd4\"", TeamId.class));
    }
}
