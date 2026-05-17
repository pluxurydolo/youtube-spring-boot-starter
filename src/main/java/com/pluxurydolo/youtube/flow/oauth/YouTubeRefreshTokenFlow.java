package com.pluxurydolo.youtube.flow.oauth;

import com.pluxurydolo.youtube.dto.YouTubeTokens;
import com.pluxurydolo.youtube.exception.YouTubeRefreshTokenException;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class YouTubeRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeRefreshTokenFlow.class);

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
            .map(credentials -> credentials.getCredentials().getAuthenticationType())
            .doOnSuccess(_ -> LOGGER.info("bqkn [youtube-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("dkhb [youtube-starter] Произошла ошибка при обновлении access token]");
                return Mono.error(new YouTubeRefreshTokenException(throwable));
            });
    }
}
