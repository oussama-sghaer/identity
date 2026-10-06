package com.training.identity.config;

import com.training.identity.domain.UserId;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdMappingConfigurationTest {

    private ObjectMapper newMapperWithModule() {
        return JsonMapper.builder()
                .addModule(new UserIdMappingConfiguration().userIdJacksonModule())
                .build();
    }

    @Test
    void should_serialize_user_id_as_bare_uuid_string() {
        var mapper = newMapperWithModule();

        var userId = new UserId(UUID.fromString("b3554525-d56c-44ea-8b47-d5d2d0438efb"));
        String json = mapper.writeValueAsString(userId);

        assertEquals("\"b3554525-d56c-44ea-8b47-d5d2d0438efb\"", json);
    }

    @Test
    void should_deserialize_bare_uuid_string_into_user_id() {
        var mapper = newMapperWithModule();

        UserId userId = mapper.readValue("\"b3554525-d56c-44ea-8b47-d5d2d0438efb\"", UserId.class);

        assertEquals(new UserId(UUID.fromString("b3554525-d56c-44ea-8b47-d5d2d0438efb")), userId);
    }
}
