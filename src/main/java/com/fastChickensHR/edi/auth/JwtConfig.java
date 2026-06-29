/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

    private String secret;
    private long accessTokenExpirySeconds = 900;
    private long refreshTokenExpirySeconds = 604800;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getAccessTokenExpirySeconds() { return accessTokenExpirySeconds; }
    public void setAccessTokenExpirySeconds(long s) { this.accessTokenExpirySeconds = s; }

    public long getRefreshTokenExpirySeconds() { return refreshTokenExpirySeconds; }
    public void setRefreshTokenExpirySeconds(long s) { this.refreshTokenExpirySeconds = s; }
}
