package com.pluxurydolo.youtube.flow.oauth;

import com.pluxurydolo.youtube.dto.YouTubeTokens;
import com.pluxurydolo.youtube.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class YouTubeRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeRefreshTokenFlow.class);

    private final AbstractTokenRetriever abstractTokenRetriever;
    private final YouTubeTokenRefresher youTubeTokenRefresher;
    private final RefreshTokenFlowHook refreshTokenFlowHook;

    public YouTubeRefreshTokenFlow(
        AbstractTokenRetriever abstractTokenRetriever,
        YouTubeTokenRefresher youTubeTokenRefresher,
        RefreshTokenFlowHook refreshTokenFlowHook
    ) {
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.youTubeTokenRefresher = youTubeTokenRefresher;
        this.refreshTokenFlowHook = refreshTokenFlowHook;
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .map(YouTubeTokens::refreshToken)
            .flatMap(youTubeTokenRefresher::refresh)
            .flatMap(_ -> refreshTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("bqkn [youtube-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("dkhb [youtube-starter] Произошла ошибка при обновлении access token");
                return refreshTokenFlowHook.handleException(throwable);
            });
    }
}
