package com.training.identity.domain;

import java.util.UUID;

public record TeamId(UUID id) {

    public TeamId{
        if (id == null) throw new IllegalArgumentException("Id cannot be null");
    }
    public static TeamId random(){
        return new TeamId(UUID.randomUUID());
    }
}
