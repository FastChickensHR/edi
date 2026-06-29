/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import com.fastChickensHR.edi.identity.MemberRole;
import com.fastChickensHR.edi.identity.persistence.ApiKeyEntity;
import com.fastChickensHR.edi.identity.persistence.ApiKeyRepository;
import com.fastChickensHR.edi.identity.persistence.OrganizationMemberEntity;
import com.fastChickensHR.edi.identity.persistence.OrganizationMemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Authenticates requests that carry an {@code X-Api-Key} header.
 * Runs before the JWT bearer token filter so that API key clients
 * are not required to provide a Bearer token.
 *
 * <p>Authentication flow:
 * <ol>
 *   <li>SHA-256 hash the raw key from the header.</li>
 *   <li>Look up the hash in the database (partial index makes this fast).</li>
 *   <li>Reject if the key is expired or revoked.</li>
 *   <li>Resolve the caller's role from their org membership.</li>
 *   <li>Set the SecurityContext with an {@link AuthenticatedUser} principal.</li>
 * </ol>
 */
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    static final String API_KEY_HEADER = "X-Api-Key";

    private final ApiKeyRepository apiKeyRepository;
    private final OrganizationMemberRepository memberRepository;
    private final TokenHashService tokenHashService;

    public ApiKeyAuthenticationFilter(ApiKeyRepository apiKeyRepository,
                                      OrganizationMemberRepository memberRepository,
                                      TokenHashService tokenHashService) {
        this.apiKeyRepository = apiKeyRepository;
        this.memberRepository = memberRepository;
        this.tokenHashService = tokenHashService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String rawKey = request.getHeader(API_KEY_HEADER);
        if (rawKey == null || rawKey.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String keyHash = tokenHashService.hash(rawKey);
        Optional<ApiKeyEntity> keyOpt = apiKeyRepository.findCurrentByKeyHash(keyHash);

        if (keyOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        ApiKeyEntity apiKey = keyOpt.get();

        if (isExpiredOrRevoked(apiKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<OrganizationMemberEntity> memberOpt = memberRepository
                .findCurrentByOrganizationIdAndUserId(apiKey.getOrganizationId(), apiKey.getCreatedByUserId());

        if (memberOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        MemberRole role = memberOpt.get().getRole();
        AuthenticatedUser principal = new AuthenticatedUser(
                apiKey.getCreatedByUserId(), apiKey.getOrganizationId(), role);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                principal, null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private boolean isExpiredOrRevoked(ApiKeyEntity key) {
        if (key.getRevokedAt() != null) return true;
        if (key.getExpiresAt() != null && key.getExpiresAt().isBefore(Instant.now())) return true;
        return false;
    }
}
