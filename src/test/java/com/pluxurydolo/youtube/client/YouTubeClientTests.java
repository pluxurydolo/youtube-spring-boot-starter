package com.pluxurydolo.youtube.client;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.step.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeClientTests {

    @Mock
    private YouTubeInstanceBuilder youTubeInstanceBuilder;

    @Mock
    private YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder;

    @Mock
    private YouTubeVideoStatusBuilder youTubeVideoStatusBuilder;

    @Mock
    private YouTubeVideoBuilder youTubeVideoBuilder;

    @Mock
    private YouTubeVideoUploader youTubeVideoUploader;

    @Mock
    private VideoSnippet videoSnippet;

    @Mock
    private VideoStatus videoStatus;

    @Mock
    private Video video;

    @Mock
    private YouTube youTube;

    @InjectMocks
    private YouTubeClient youTubeClient;

    @Test
    void testUploadVideo() {
        when(youTubeVideoSnippetBuilder.build(anyString(), anyString(), any()))
            .thenReturn(videoSnippet);
        when(youTubeVideoStatusBuilder.build())
            .thenReturn(videoStatus);
        when(youTubeVideoBuilder.build(any(), any()))
            .thenReturn(video);
        when(youTubeInstanceBuilder.build())
            .thenReturn(Mono.just(youTube));
        when(youTubeVideoUploader.upload(any(), any(), anyList(), any()))
            .thenReturn(Mono.just(video));

        Mono<String> result = youTubeClient.uploadVideo(uploadVideoRequest());

        create(result)
            .expectNext("title")
            .verifyComplete();
    }

    private static UploadVideoRequest uploadVideoRequest() {
        byte[] bytes = {};
        String title = "title";
        String description = "description";
        List<String> tags = List.of("tag1", "tag2");
        return new UploadVideoRequest(bytes, title, description, tags);
    }
}
