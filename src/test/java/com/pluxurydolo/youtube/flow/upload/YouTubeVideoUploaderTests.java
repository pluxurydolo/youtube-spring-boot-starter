package com.pluxurydolo.youtube.flow.upload;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeVideoUploaderTests {

    @Mock
    private YouTubeInstanceBuilder youTubeInstanceBuilder;

    @Mock
    private YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder;

    @Mock
    private YouTubeVideoStatusBuilder youTubeVideoStatusBuilder;

    @Mock
    private YouTubeVideoBuilder youTubeVideoBuilder;

    @Mock
    private MediaHttpUploaderProgressListener progressListener;

    @Mock
    private YouTube youTube;

    @Mock
    private VideoSnippet videoSnippet;

    @Mock
    private VideoStatus videoStatus;

    @Mock
    private Video video;

    @Mock
    private YouTube.Videos videos;

    @Mock
    private YouTube.Videos.Insert insert;

    @Mock
    private MediaHttpUploader mediaHttpUploader;

    @InjectMocks
    private YouTubeVideoUploader youTubeVideoUploader;

    @Test
    void testUpload() throws IOException {
        when(youTubeInstanceBuilder.build())
            .thenReturn(Mono.just(youTube));
        when(youTubeVideoSnippetBuilder.build(anyString(), anyString(), any()))
            .thenReturn(videoSnippet);
        when(youTubeVideoStatusBuilder.build())
            .thenReturn(videoStatus);
        when(youTubeVideoBuilder.build(any(), any()))
            .thenReturn(video);
        when(youTube.videos())
            .thenReturn(videos);
        when(videos.insert(anyList(), any(), any()))
            .thenReturn(insert);
        when(insert.getMediaHttpUploader())
            .thenReturn(mediaHttpUploader);
        when(mediaHttpUploader.setDirectUploadEnabled(anyBoolean()))
            .thenReturn(mediaHttpUploader);
        when(mediaHttpUploader.setProgressListener(progressListener))
            .thenReturn(mediaHttpUploader);
        when(insert.execute())
            .thenReturn(video);

        Mono<String> result = youTubeVideoUploader.upload(uploadVideoRequest());

        create(result)
            .expectNext("title")
            .verifyComplete();
    }

    @Test
    void testUploadWhenExceptionOccurred() {
        when(youTubeInstanceBuilder.build())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = youTubeVideoUploader.upload(uploadVideoRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }

    private static UploadVideoRequest uploadVideoRequest() {
        byte[] bytes = {};
        List<String> tags = List.of("tag1", "tag2");
        return new UploadVideoRequest(bytes, "title", "description", tags);
    }
}
