package com.training.identity.domain;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId{
        if(id == null) throw new IllegalArgumentException("Id cannot be null");
    }
    
    public static UserId random(){
        return new UserId(UUID.randomUUID());
    }

}
