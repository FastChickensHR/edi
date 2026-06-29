/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import com.fastChickensHR.edi.identity.MemberRole;
import com.fastChickensHR.edi.identity.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OrganizationMemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final TokenHashService tokenHashService;
    private final JwtConfig jwtConfig;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       OrganizationMemberRepository memberRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       JwtService jwtService,
                       TokenHashService tokenHashService,
                       JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.tokenHashService = tokenHashService;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        UserEntity user = userRepository.findCurrentByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        OrganizationMemberEntity membership = memberRepository
                .findCurrentByOrganizationIdAndUserId(request.organizationId(), user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "User is not a member of this organization"));

        MemberRole role = membership.getRole();
        String accessToken = jwtService.issueAccessToken(user.getUserId(), request.organizationId(), role);
        String rawRefreshToken = issueRefreshToken(user.getUserId());

        return new TokenResponse(accessToken, rawRefreshToken, jwtConfig.getAccessTokenExpirySeconds());
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest request) {
        String tokenHash = tokenHashService.hash(request.refreshToken());

        RefreshTokenEntity existing = refreshTokenRepository.findCurrentByTokenHash(tokenHash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token"));

        // Revoke the used token (rolling refresh — prevents replay)
        existing.setValidTo(Instant.now());
        refreshTokenRepository.save(existing);

        // Re-resolve org context from the latest active membership for this user.
        // For simplicity, pick the first active membership; full multi-org flows belong in a later phase.
        OrganizationMemberEntity membership = memberRepository
                .findAllCurrentByUserId(existing.getUserId())
                .stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No active organization membership"));

        MemberRole role = membership.getRole();
        String accessToken = jwtService.issueAccessToken(
                existing.getUserId(), membership.getOrganizationId(), role);
        String rawRefreshToken = issueRefreshToken(existing.getUserId());

        return new TokenResponse(accessToken, rawRefreshToken, jwtConfig.getAccessTokenExpirySeconds());
    }

    @Transactional
    public void revoke(RefreshRequest request) {
        String tokenHash = tokenHashService.hash(request.refreshToken());
        refreshTokenRepository.findCurrentByTokenHash(tokenHash).ifPresent(token -> {
            token.setValidTo(Instant.now());
            refreshTokenRepository.save(token);
        });
    }

    private String issueRefreshToken(UUID userId) {
        String rawToken = UUID.randomUUID().toString();
        String tokenHash = tokenHashService.hash(rawToken);
        Instant now = Instant.now();

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setTokenId(UUID.randomUUID());
        entity.setUserId(userId);
        entity.setSysFrom(now);
        entity.setSysTo(RefreshTokenEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(RefreshTokenEntity.TEMPORAL_INFINITY);
        entity.setTokenHash(tokenHash);
        entity.setExpiresAt(now.plusSeconds(jwtConfig.getRefreshTokenExpirySeconds()));

        refreshTokenRepository.save(entity);
        return rawToken;
    }
}
