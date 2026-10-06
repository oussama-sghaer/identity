package com.training.identity.config;

import com.training.identity.domain.UserId;
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
public class UserIdMappingConfiguration {

    static class UserIdSerializer extends ValueSerializer<UserId> {
        @Override
        public void serialize(UserId value, JsonGenerator gen, SerializationContext ctxt) {
            gen.writeString(value.id().toString());
        }
    }

    static class UserIdDeserializer extends ValueDeserializer<UserId> {
        @Override
        public UserId deserialize(JsonParser p, DeserializationContext ctxt) {
            String text = p.getText();
            var uuid = UUID.fromString(text);
            return new UserId(uuid);
        }
    }

    @Bean
    public JacksonModule userIdJacksonModule() {
        var module = new SimpleModule();
        module.addDeserializer(UserId.class, new UserIdDeserializer());
        module.addSerializer(UserId.class, new UserIdSerializer());
        return module;
    }
}
