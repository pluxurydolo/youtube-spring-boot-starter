package com.pluxurydolo.youtube.client;

import com.pluxurydolo.youtube.dto.request.PublishVideoRequest;
import com.pluxurydolo.youtube.exception.YouTubeVideoPublicationException;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class YouTubeVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeVideoClient.class);

    private final YouTubeVideoPublisher youTubeVideoPublisher;

    public YouTubeVideoClient(YouTubeVideoPublisher youTubeVideoPublisher) {
        this.youTubeVideoPublisher = youTubeVideoPublisher;
    }

    public Mono<String> publishVideo(PublishVideoRequest request) {
        return youTubeVideoPublisher.publish(request)
            .doOnSuccess(_ -> LOGGER.info("jwgk [youtube-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("esos [youtube-starter] Произошла ошибка при публикации видео");
                return Mono.error(new YouTubeVideoPublicationException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
