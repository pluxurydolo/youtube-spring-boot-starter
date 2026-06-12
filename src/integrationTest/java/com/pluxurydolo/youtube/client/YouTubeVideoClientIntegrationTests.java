package com.pluxurydolo.youtube.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.youtube.base.AbstractIntegrationTests;
import com.pluxurydolo.youtube.dto.request.PublishVideoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

class YouTubeVideoClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(YouTubeVideoClient.class);

    @Autowired
    private YouTubeVideoClient youTubeVideoClient;

    @Test
    void testPublishVideo() {
        List<ILoggingEvent> logs = listAppender().list;

        youTubeVideoClient.publishVideo(publishVideoRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("jwgk [youtube-starter] Видео успешно опубликовано");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static PublishVideoRequest publishVideoRequest() {
        return new PublishVideoRequest(bytes(), "title", "description", tags());
    }

    private static byte[] bytes() {
        return new byte[]{1, 2, 3};
    }

    private static List<String> tags() {
        return List.of("tag1", "tag2");
    }
}
