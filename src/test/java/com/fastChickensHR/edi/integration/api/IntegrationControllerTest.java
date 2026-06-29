/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.api;

import com.fastChickensHR.edi.auth.AuthenticatedUser;
import com.fastChickensHR.edi.auth.SecurityContextHelper;
import com.fastChickensHR.edi.identity.MemberRole;
import com.fastChickensHR.edi.integration.persistence.IntegrationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntegrationControllerTest {

    @Mock
    private IntegrationService service;

    @Mock
    private IntegrationMapper mapper;

    @InjectMocks
    private IntegrationController controller;

    private static final UUID INTEGRATION_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final UUID ORGANIZATION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID CREATED_BY_USER_ID = UUID.fromString("660e8400-e29b-41d4-a716-446655440001");

    private static AuthenticatedUser caller() {
        return new AuthenticatedUser(CREATED_BY_USER_ID, ORGANIZATION_ID, MemberRole.VIEWER);
    }

    private static IntegrationResponse sampleResponse() {
        return new IntegrationResponse(
                INTEGRATION_ID, "Test Integration", ORGANIZATION_ID, CREATED_BY_USER_ID,
                "FASTCHICKENS_HR", "STATE_OF_MICHIGAN", "X12_834",
                "OUTBOUND", Instant.now());
    }

    private static IntegrationRequest sampleRequest() {
        return new IntegrationRequest(
                "Test Integration", ORGANIZATION_ID, CREATED_BY_USER_ID,
                "FASTCHICKENS_HR", "STATE_OF_MICHIGAN", "X12_834");
    }

    // --- GET /integrations ---

    @Test
    void findAll_returnsMappedResponses() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            IntegrationEntity entity = new IntegrationEntity();
            IntegrationResponse response = sampleResponse();
            when(service.findAll(ORGANIZATION_ID)).thenReturn(List.of(entity));
            when(mapper.toResponse(entity)).thenReturn(response);

            List<IntegrationResponse> result = controller.findAll();

            assertEquals(1, result.size());
            assertSame(response, result.get(0));
        }
    }

    @Test
    void findAll_returnsEmptyListWhenNoneExist() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            when(service.findAll(ORGANIZATION_ID)).thenReturn(List.of());

            assertTrue(controller.findAll().isEmpty());
        }
    }

    // --- GET /integrations/{id} ---

    @Test
    void findById_returns200WithBodyWhenFound() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            IntegrationEntity entity = new IntegrationEntity();
            IntegrationResponse response = sampleResponse();
            when(service.findById(INTEGRATION_ID, ORGANIZATION_ID)).thenReturn(Optional.of(entity));
            when(mapper.toResponse(entity)).thenReturn(response);

            ResponseEntity<IntegrationResponse> result = controller.findById(INTEGRATION_ID);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertSame(response, result.getBody());
        }
    }

    @Test
    void findById_returns404WhenNotFound() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            when(service.findById(INTEGRATION_ID, ORGANIZATION_ID)).thenReturn(Optional.empty());

            ResponseEntity<IntegrationResponse> result = controller.findById(INTEGRATION_ID);

            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
            assertNull(result.getBody());
        }
    }

    // --- POST /integrations ---

    @Test
    void create_returnsMappedResponseBody() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            IntegrationRequest request = sampleRequest();
            IntegrationEntity entity = new IntegrationEntity();
            IntegrationResponse response = sampleResponse();
            when(service.create(request)).thenReturn(entity);
            when(mapper.toResponse(entity)).thenReturn(response);

            IntegrationResponse result = controller.create(request);

            assertSame(response, result);
        }
    }

    // --- PUT /integrations/{id} ---

    @Test
    void update_returns200WithBodyWhenFound() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            IntegrationRequest request = sampleRequest();
            IntegrationEntity entity = new IntegrationEntity();
            IntegrationResponse response = sampleResponse();
            when(service.update(INTEGRATION_ID, ORGANIZATION_ID, request)).thenReturn(Optional.of(entity));
            when(mapper.toResponse(entity)).thenReturn(response);

            ResponseEntity<IntegrationResponse> result = controller.update(INTEGRATION_ID, request);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertSame(response, result.getBody());
        }
    }

    @Test
    void update_returns404WhenIntegrationNotFound() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            when(service.update(INTEGRATION_ID, ORGANIZATION_ID, sampleRequest())).thenReturn(Optional.empty());

            ResponseEntity<IntegrationResponse> result = controller.update(INTEGRATION_ID, sampleRequest());

            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        }
    }

    // --- DELETE /integrations/{id} ---

    @Test
    void delete_returns204WhenDeleted() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            when(service.delete(INTEGRATION_ID, ORGANIZATION_ID)).thenReturn(true);

            ResponseEntity<Void> result = controller.delete(INTEGRATION_ID);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        }
    }

    @Test
    void delete_returns404WhenNotFound() {
        try (MockedStatic<SecurityContextHelper> helper = Mockito.mockStatic(SecurityContextHelper.class)) {
            helper.when(SecurityContextHelper::getCurrentUser).thenReturn(caller());
            when(service.delete(INTEGRATION_ID, ORGANIZATION_ID)).thenReturn(false);

            ResponseEntity<Void> result = controller.delete(INTEGRATION_ID);

            assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        }
    }
}
