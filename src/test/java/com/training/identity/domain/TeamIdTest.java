package com.training.identity.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TeamIdTest {

    @Test
    void should_create_team_with_valid_uuid(){
        var id = UUID.randomUUID();
        var teamId = new TeamId(id);
        assertEquals(id, teamId.id());
    }

    @Test
    void should_reject_null_uuid(){
        assertThrows(IllegalArgumentException.class,()-> new TeamId(null));
    }
    @Test
    void random_should_return_different_ids_each_call(){
        var first = TeamId.random();
        var second = TeamId.random();
        assertNotEquals(first, second);
    }

}