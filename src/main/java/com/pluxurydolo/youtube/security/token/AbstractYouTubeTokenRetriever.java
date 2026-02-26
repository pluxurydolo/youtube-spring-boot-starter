package com.pluxurydolo.youtube.security.token;

import com.pluxurydolo.youtube.dto.YouTubeTokens;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractYouTubeTokenRetriever {
    public Mono<YouTubeTokens> retrieve() {
        return retrieveTokens()
            .map(AbstractYouTubeTokenRetriever::mapToTokens);
    }

    private static YouTubeTokens mapToTokens(Map<String, String> tokens) {
        String accessToken = tokens.get("access_token");
        String refreshToken = tokens.get("refresh_token");
        return new YouTubeTokens(accessToken, refreshToken);
    }

    protected abstract Mono<Map<String, String>> retrieveTokens();
}
