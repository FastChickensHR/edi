/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import com.fastChickensHR.edi.identity.MemberRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Extracts the resolved {@link AuthenticatedUser} principal from the current
 * security context regardless of which auth path was used (JWT or API key).
 *
 * <p>JWT path: the principal is a {@link Jwt}; userId, organizationId, and role
 * are read from the {@code sub}, {@code org}, and {@code role} claims.</p>
 *
 * <p>API key path: the principal is already an {@link AuthenticatedUser} set
 * directly by {@link ApiKeyAuthenticationFilter}.</p>
 */
public final class SecurityContextHelper {

    private SecurityContextHelper() {}

    public static AuthenticatedUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof AuthenticatedUser user) {
            return user;
        }

        if (principal instanceof Jwt jwt) {
            return new AuthenticatedUser(
                    UUID.fromString(jwt.getSubject()),
                    UUID.fromString(jwt.getClaimAsString("org")),
                    MemberRole.valueOf(jwt.getClaimAsString("role"))
            );
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unrecognized principal type");
    }
}
