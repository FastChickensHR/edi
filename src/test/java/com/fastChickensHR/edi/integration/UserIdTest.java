/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    private static final UUID TEST_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    @Test
    void testOfUUID() {
        UserId userId = UserId.of(TEST_UUID);
        assertEquals(TEST_UUID, userId.getValue());
    }

    @Test
    void testOfString() {
        UserId userId = UserId.of("550e8400-e29b-41d4-a716-446655440000");
        assertEquals(TEST_UUID, userId.getValue());
    }

    @Test
    void testEquality() {
        UserId a = UserId.of(TEST_UUID);
        UserId b = UserId.of(TEST_UUID);
        assertEquals(a, b);
    }

    @Test
    void testInequalityForDifferentUUIDs() {
        UserId a = UserId.of(UUID.randomUUID());
        UserId b = UserId.of(UUID.randomUUID());
        assertNotEquals(a, b);
    }

    @Test
    void testOfStringThrowsOnInvalidUUID() {
        assertThrows(IllegalArgumentException.class, () -> UserId.of("not-a-uuid"));
    }
}
