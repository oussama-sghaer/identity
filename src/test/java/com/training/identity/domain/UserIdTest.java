package com.training.identity.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void should_create_user_id_with_valid_uuid() {
        var id = UUID.randomUUID();
        var userId = new UserId(id);
        assertEquals(id, userId.id());
    }

    @Test
    void should_reject_null_uuid() {
        assertThrows(IllegalArgumentException.class,()-> new UserId(null));
    }

    @Test
    void random_should_return_different_ids_each_call() {
        var first = UserId.random();
        var second = UserId.random();

        assertNotEquals(first, second);
    }
}