package com.pluxurydolo.youtube.client;

import com.pluxurydolo.youtube.dto.request.PublishVideoRequest;
import com.pluxurydolo.youtube.exception.YouTubeVideoPublicationException;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeVideoClientTests {

    @Mock
    private YouTubeVideoPublisher youTubeVideoPublisher;

    @InjectMocks
    private YouTubeVideoClient youTubeVideoClient;

    @Test
    void testPublishVideo() {
        when(youTubeVideoPublisher.publish(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeVideoClient.publishVideo(publishVideoRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testPublishVideoWhenExceptionOccurred() {
        when(youTubeVideoPublisher.publish(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = youTubeVideoClient.publishVideo(publishVideoRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(YouTubeVideoPublicationException.class));
    }

    private static PublishVideoRequest publishVideoRequest() {
        byte[] bytes = {};
        String title = "title";
        String description = "description";
        List<String> tags = List.of("tag1", "tag2");
        return new PublishVideoRequest(bytes, title, description, tags);
    }
}
