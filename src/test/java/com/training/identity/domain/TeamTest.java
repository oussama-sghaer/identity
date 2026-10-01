package com.training.identity.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    public void should_create_team_with_valid_id_and_name(){
        var id =TeamId.random();
        var name = "Team 1";
        var team = new Team(id, name);
        assertEquals(id, team.id());
        assertEquals(name, team.name());
    }

    @Test
    public void should_reject_null_name(){
        assertThrows(IllegalArgumentException.class,()-> new Team(TeamId.random(), null));
    }

    @Test
    public void should_reject_blank_name(){
        assertThrows(IllegalArgumentException.class,()-> new Team(TeamId.random(), " "));
    }

    @Test
    public void should_reject_null_id(){
        assertThrows(IllegalArgumentException.class,()->new Team(null, "Team 1"));
    }

}