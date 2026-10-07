package com.training.identity.config.domain;

import com.training.identity.domain.TeamId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.module.SimpleModule;

import java.util.UUID;

@Configuration
public class TeamIdMappingConfiguration {

    static class TeamIdSerializer extends ValueSerializer<TeamId> {
        @Override
        public void serialize(TeamId value, JsonGenerator gen, SerializationContext ctxt) {
            gen.writeString(value.id().toString());
        }
    }

    static class TeamIdDeserializer extends ValueDeserializer<TeamId> {
        @Override
        public TeamId deserialize(JsonParser p, DeserializationContext ctxt) {
            String text = p.getText();
            var uuid = UUID.fromString(text);
            return new TeamId(uuid);
        }
    }

    @Bean
    public JacksonModule teamIdJacksonModule() {
        var module = new SimpleModule();
        module.addDeserializer(TeamId.class, new TeamIdDeserializer());
        module.addSerializer(TeamId.class, new TeamIdSerializer());
        return module;
    }
}
