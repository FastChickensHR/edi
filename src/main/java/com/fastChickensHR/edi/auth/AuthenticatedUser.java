/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import com.fastChickensHR.edi.identity.MemberRole;

import java.util.UUID;

/**
 * The resolved principal placed in the SecurityContext after successful authentication.
 * Both the JWT filter and the API key filter produce an instance of this type.
 */
public record AuthenticatedUser(UUID userId, UUID organizationId, MemberRole role) {
}
