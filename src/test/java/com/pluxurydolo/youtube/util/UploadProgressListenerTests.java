package com.pluxurydolo.youtube.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_COMPLETE;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_STARTED;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_COMPLETE;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(MockitoExtension.class)
class UploadProgressListenerTests {
    private static final UploadProgressListener LISTENER = new UploadProgressListener();
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(UploadProgressListener.class);

    @Mock
    private MediaHttpUploader mediaHttpUploader;

    @Test
    void testProgressChangedWhenUploadStateIsNull() {
        when(mediaHttpUploader.getUploadState())
            .thenReturn(null);

        List<ILoggingEvent> logs = listAppender().list;
        LISTENER.progressChanged(mediaHttpUploader);

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("zxkf [youtube-starter] Статус загрузки неизвестен");
            });
    }

    @Test
    void testProgressChangedWhenUploadStateIsInitiationStarted() {
        when(mediaHttpUploader.getUploadState())
            .thenReturn(INITIATION_STARTED);

        List<ILoggingEvent> logs = listAppender().list;
        LISTENER.progressChanged(mediaHttpUploader);

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("sruc [youtube-starter] Инициализация загрузки видео");
            });
    }

    @Test
    void testProgressChangedWhenUploadStateIsInitiationComplete() {
        when(mediaHttpUploader.getUploadState())
            .thenReturn(INITIATION_COMPLETE);

        List<ILoggingEvent> logs = listAppender().list;
        LISTENER.progressChanged(mediaHttpUploader);

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("ytwm [youtube-starter] Инициализация загрузки видео завершена");
            });
    }

    @Test
    void testProgressChangedWhenUploadStateIsMediaInProgress() throws IOException {
        when(mediaHttpUploader.getUploadState())
            .thenReturn(MEDIA_IN_PROGRESS);
        when(mediaHttpUploader.getProgress())
            .thenReturn(0.336);

        List<ILoggingEvent> logs = listAppender().list;
        LISTENER.progressChanged(mediaHttpUploader);

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("bxnk [youtube-starter] Загружено: 33.6%");
            });
    }

    @Test
    void testProgressChangedWhenUploadStateIsMediaComplete() {
        when(mediaHttpUploader.getUploadState())
            .thenReturn(MEDIA_COMPLETE);

        List<ILoggingEvent> logs = listAppender().list;
        LISTENER.progressChanged(mediaHttpUploader);

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("hnlj [youtube-starter] Загрузка видео завершена");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }
}
