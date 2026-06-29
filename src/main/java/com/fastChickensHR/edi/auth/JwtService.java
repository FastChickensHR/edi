/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import com.fastChickensHR.edi.identity.MemberRole;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtConfig config;

    public JwtService(JwtEncoder encoder, JwtConfig config) {
        this.encoder = encoder;
        this.config = config;
    }

    public String issueAccessToken(UUID userId, UUID organizationId, MemberRole role) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(config.getAccessTokenExpirySeconds());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiresAt(expiry)
                .claim("org", organizationId.toString())
                .claim("role", role.name())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
