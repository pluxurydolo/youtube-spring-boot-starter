package com.pluxurydolo.youtube.client;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.step.VideoBuilder;
import com.pluxurydolo.youtube.step.VideoSender;
import com.pluxurydolo.youtube.step.VideoSnippetBuilder;
import com.pluxurydolo.youtube.step.VideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeClientTests {

    @Mock
    private YouTubeInstanceBuilder youTubeInstanceBuilder;

    @Mock
    private VideoSnippetBuilder videoSnippetBuilder;

    @Mock
    private VideoStatusBuilder videoStatusBuilder;

    @Mock
    private VideoBuilder videoBuilder;

    @Mock
    private VideoSender videoSender;

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
        when(videoSnippetBuilder.build(anyString(), anyString(), any()))
            .thenReturn(videoSnippet);
        when(videoStatusBuilder.build())
            .thenReturn(videoStatus);
        when(videoBuilder.build(any(), any()))
            .thenReturn(video);
        when(youTubeInstanceBuilder.build())
            .thenReturn(Mono.just(youTube));
        when(videoSender.sendVideo(any(), any(), anyList(), any()))
            .thenReturn(Mono.just(video));

        Mono<String> result = youTubeClient.uploadVideo(uploadVideoRequest());

        create(result)
            .expectNext("title")
            .verifyComplete();
    }

    private static UploadVideoRequest uploadVideoRequest() {
        File file = mock(File.class);
        String title = "title";
        String description = "description";
        String[] tags = {"tag1", "tag2"};
        return new UploadVideoRequest(file, title, description, tags);
    }
}
