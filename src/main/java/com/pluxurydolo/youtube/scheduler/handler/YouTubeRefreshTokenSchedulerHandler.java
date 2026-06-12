package com.pluxurydolo.youtube.scheduler.handler;

import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class YouTubeRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeRefreshTokenSchedulerHandler.class);

    private final YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;
    private final RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook;

    public YouTubeRefreshTokenSchedulerHandler(
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        this.youTubeRefreshTokenFlow = youTubeRefreshTokenFlow;
        this.refreshTokenSchedulerHandlerHook = refreshTokenSchedulerHandlerHook;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("xqgm [youtube-starter] Стартовала джоба {}", jobName);

        return youTubeRefreshTokenFlow.refreshToken()
            .flatMap(_ -> refreshTokenSchedulerHandlerHook.doAfter())
            .doOnSuccess(_ -> LOGGER.info("bbaf [youtube-starter] Джоба {} успешно завершена", jobName))
            .onErrorResume(throwable -> {
                LOGGER.error("uhbl [youtube-starter] Произошла ошибка при завершении джобы {}", jobName);
                return refreshTokenSchedulerHandlerHook.handleException(throwable, jobName);
            });
    }
}
