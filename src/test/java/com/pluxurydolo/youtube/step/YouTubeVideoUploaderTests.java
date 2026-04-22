package com.pluxurydolo.youtube.step;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeVideoUploaderTests {

    @Mock
    private MediaHttpUploaderProgressListener progressListener;

    @Mock
    private YouTube youTube;

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

        Mono<Video> result = youTubeVideoUploader.upload(bytes(), youTube, List.of("parts"), video);

        create(result)
            .expectNext(video)
            .verifyComplete();
    }

    @Test
    void testUploadWhenExceptionOccurred() throws IOException {
        when(youTube.videos())
            .thenReturn(videos);
        doThrow(IOException.class)
            .when(videos).insert(anyList(), any(), any());

        Mono<Video> result = youTubeVideoUploader.upload(bytes(), youTube, List.of("parts"), video);

        create(result)
            .expectError(YouTubeUploadException.class)
            .verify();
    }

    private static byte[] bytes() {
        return new byte[]{};
    }
}
