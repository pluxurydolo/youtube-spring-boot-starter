package com.pluxurydolo.youtube.client;

import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class YouTubeVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeVideoClient.class);

    private final YouTubeVideoUploader youTubeVideoUploader;

    public YouTubeVideoClient(YouTubeVideoUploader youTubeVideoUploader) {
        this.youTubeVideoUploader = youTubeVideoUploader;
    }

    public Mono<String> uploadVideo(UploadVideoRequest request) {
        return youTubeVideoUploader.upload(request)
            .doOnSuccess(_ -> LOGGER.info("jwgk [youtube-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.error("esos [youtube-starter] Произошла ошибка при публикации видео");
                return Mono.error(new YouTubeUploadException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
