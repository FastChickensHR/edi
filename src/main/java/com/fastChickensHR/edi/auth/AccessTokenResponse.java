/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

/**
 * JSON response sent to the client after login or refresh.
 * The refresh token is intentionally omitted — it is delivered via an httpOnly cookie
 * and must never be exposed to JavaScript.
 */
public record AccessTokenResponse(
        String accessToken,
        long expiresIn
) {
}
