package com.pluxurydolo.youtube.client;

import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoUploader;
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
    private YouTubeVideoUploader youTubeVideoUploader;

    @InjectMocks
    private YouTubeVideoClient youTubeVideoClient;

    @Test
    void testUploadVideo() {
        when(youTubeVideoUploader.upload(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeVideoClient.uploadVideo(uploadVideoRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testUploadVideoWhenExceptionOccurred() {
        when(youTubeVideoUploader.upload(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = youTubeVideoClient.uploadVideo(uploadVideoRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(YouTubeUploadException.class));
    }

    private static UploadVideoRequest uploadVideoRequest() {
        byte[] bytes = {};
        String title = "title";
        String description = "description";
        List<String> tags = List.of("tag1", "tag2");
        return new UploadVideoRequest(bytes, title, description, tags);
    }
}
