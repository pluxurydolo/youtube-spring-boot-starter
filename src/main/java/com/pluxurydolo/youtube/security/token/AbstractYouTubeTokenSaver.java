package com.pluxurydolo.youtube.security.token;

import com.google.api.client.auth.oauth2.TokenResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractYouTubeTokenSaver {
    public Mono<String> save(TokenResponse tokenResponse) {
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresInSeconds = tokenResponse.getExpiresInSeconds();
        String tokenType = tokenResponse.getTokenType();
        String scope = tokenResponse.getScope();

        Map<String, String> tokens = Map.of(
            "access_token", accessToken,
            "refresh_token", refreshToken,
            "expires_in", String.valueOf(expiresInSeconds),
            "token_type", tokenType,
            "scope", scope
        );

        return saveTokens(tokens);
    }

    protected abstract Mono<String> saveTokens(Map<String, String> tokens);
}
