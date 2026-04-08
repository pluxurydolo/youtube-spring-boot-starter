package com.pluxurydolo.youtube.token;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.auth.oauth2.AccessToken;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static java.lang.String.join;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class AbstractTokenSaver {
    public Mono<String> save(TokenResponse response) {
        String accessToken = response.getAccessToken();
        String refreshToken = response.getRefreshToken();
        Long expiresInSeconds = response.getExpiresInSeconds();
        String tokenType = response.getTokenType();
        String scope = response.getScope();
        String updatedAt = updatedAt();

        Map<String, String> tokens = Map.of(
            "access_token", accessToken,
            "refresh_token", refreshToken,
            "expires_in", String.valueOf(expiresInSeconds),
            "token_type", tokenType,
            "scope", scope,
            "updated_at", updatedAt
        );

        return saveTokens(tokens);
    }

    public Mono<String> save(AccessToken response, String refreshToken) {
        String accessToken = response.getTokenValue();
        Long expiresInSeconds = response.getExpirationTime().getTime();
        String tokenType = "Bearer";
        String scope = scope(response);
        String updatedAt = updatedAt();

        Map<String, String> tokens = Map.of(
            "access_token", accessToken,
            "refresh_token", refreshToken,
            "expires_in", String.valueOf(expiresInSeconds),
            "token_type", tokenType,
            "scope", scope,
            "updated_at", updatedAt
        );

        return saveTokens(tokens);
    }

    private static String scope(AccessToken response) {
        List<String> scopes = response.getScopes();
        return join(" ", scopes);
    }

    private static String updatedAt() {
        LocalDateTime now = now(ZoneId.of("Europe/Moscow"));
        return now.format(ofPattern("yyyy-MM-dd HH:mm:ss 'МСК'"));
    }

    protected abstract Mono<String> saveTokens(Map<String, String> tokens);
}
