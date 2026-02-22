package com.pluxurydolo.youtube.security.token;

import com.pluxurydolo.youtube.dto.Tokens;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokenRetriever {
    public Mono<Tokens> retrieve() {
        return retrieveTokens()
            .map(AbstractTokenRetriever::mapToTokens);
    }

    private static Tokens mapToTokens(Map<String, String> tokens) {
        String accessToken = tokens.get("access_token");
        String refreshToken = tokens.get("refresh_token");
        return new Tokens(accessToken, refreshToken);
    }

    protected abstract Mono<Map<String, String>> retrieveTokens();
}
