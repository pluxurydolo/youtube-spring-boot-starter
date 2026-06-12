package com.pluxurydolo.youtube.scheduler.handler;

import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class YouTubeRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeRefreshTokenSchedulerHandler.class);

    private final YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    public YouTubeRefreshTokenSchedulerHandler(
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow
    ) {
        this.youTubeRefreshTokenFlow = youTubeRefreshTokenFlow;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("xqgm [youtube-starter] Стартовала джоба {}", jobName);

        return youTubeRefreshTokenFlow.refreshToken()
            .doOnSuccess(_ -> LOGGER.info("bbaf [youtube-starter] Джоба {} успешно завершена", jobName))
            .doOnError(_ -> LOGGER.error("uhbl [youtube-starter] Произошла ошибка при завершении джобы {}", jobName));
    }
}
