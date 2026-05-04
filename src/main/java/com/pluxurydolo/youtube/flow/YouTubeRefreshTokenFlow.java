package com.pluxurydolo.youtube.flow;

import com.pluxurydolo.youtube.dto.YouTubeTokens;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import reactor.core.publisher.Mono;

public class YouTubeRefreshTokenFlow {
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final YouTubeTokenRefresher youTubeTokenRefresher;

    public YouTubeRefreshTokenFlow(
        AbstractTokenRetriever abstractTokenRetriever,
        YouTubeTokenRefresher youTubeTokenRefresher
    ) {
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.youTubeTokenRefresher = youTubeTokenRefresher;
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .map(YouTubeTokens::refreshToken)
            .flatMap(youTubeTokenRefresher::refresh)
            .map(credentials -> credentials.getCredentials().getAuthenticationType());
    }
}
