package com.pluxurydolo.youtube.util;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_COMPLETE;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_STARTED;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_COMPLETE;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS;
import static java.util.Locale.US;

public class YouTubeUploadProgressListener implements MediaHttpUploaderProgressListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeUploadProgressListener.class);

    @Override
    public void progressChanged(MediaHttpUploader uploader) {
        Mono.fromCallable(uploader::getUploadState)
            .doOnSuccess(uploadState -> logResult(uploader, uploadState))
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
    }

    private static void logResult(MediaHttpUploader uploader, MediaHttpUploader.UploadState uploadState) {
        if (uploadState == INITIATION_STARTED) {
            LOGGER.info("sruc [youtube-starter] Инициализация загрузки видео");
        } else if (uploadState == INITIATION_COMPLETE) {
            LOGGER.info("ytwm [youtube-starter] Инициализация загрузки видео завершена");
        } else if (uploadState == MEDIA_IN_PROGRESS) {
            String formattedProgress = formattedProgress(uploader);
            LOGGER.info("bxnk [youtube-starter] Загружено: {}%", formattedProgress);
        } else if (uploadState == MEDIA_COMPLETE) {
            LOGGER.info("hnlj [youtube-starter] Загрузка видео завершена");
        } else {
            LOGGER.info("zxkf [youtube-starter] Статус загрузки неизвестен");
        }
    }

    private static String formattedProgress(MediaHttpUploader uploader) {
        try {
            double progress = uploader.getProgress() * 100;
            return String.format(US, "%.1f", progress);
        } catch (IOException exception) {
            LOGGER.error("gvjg [youtube-starter] Произошла ошибка при получении прогресса загрузки");
            throw new YouTubeUploadException(exception);
        }
    }
}
