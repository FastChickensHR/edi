/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AccessTokenResponse login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        TokenResponse result = authService.login(request);
        setRefreshCookie(response, result.refreshToken());
        return new AccessTokenResponse(result.accessToken(), result.expiresIn());
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refresh(
            @CookieValue(name = REFRESH_TOKEN_COOKIE, required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.UNAUTHORIZED, "Missing refresh token");
        }
        TokenResponse result = authService.refresh(new RefreshRequest(refreshToken));
        setRefreshCookie(response, result.refreshToken());
        return new AccessTokenResponse(result.accessToken(), result.expiresIn());
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(
            @CookieValue(name = REFRESH_TOKEN_COOKIE, required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            authService.revoke(new RefreshRequest(refreshToken));
        }
        clearRefreshCookie(response);
        return ResponseEntity.noContent().build();
    }

    private void setRefreshCookie(HttpServletResponse response, String rawToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, rawToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // set to true when serving over HTTPS in production
        cookie.setPath("/auth");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days — matches JwtConfig.refreshTokenExpirySeconds
        response.addCookie(cookie);
    }

    private void clearRefreshCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/auth");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
