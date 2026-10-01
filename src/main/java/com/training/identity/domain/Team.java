package com.training.identity.domain;

public record Team(TeamId id,String name ){
    public Team{
        if(id == null) throw new IllegalArgumentException("Id cannot be null");
        if(name == null) throw new IllegalArgumentException("Name cannot be null");
        if(name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
    }
}
