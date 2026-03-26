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
import org.springframework.core.io.InputStreamSource;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class VideoSenderTests {

    @Mock
    private MediaHttpUploaderProgressListener progressListener;

    @Mock
    private YouTube youTube;

    @Mock
    private InputStreamSource multipartFile;

    @Mock
    private Video video;

    @Mock
    private InputStream inputStream;

    @Mock
    private YouTube.Videos videos;

    @Mock
    private YouTube.Videos.Insert insert;

    @Mock
    private MediaHttpUploader mediaHttpUploader;

    @InjectMocks
    private VideoSender videoSender;

    @Test
    void testSendVideo() throws IOException {
        when(multipartFile.getInputStream())
            .thenReturn(inputStream);
        when(youTube.videos())
            .thenReturn(videos);
        when(videos.insert(any(), any(), any()))
            .thenReturn(insert);
        when(insert.getMediaHttpUploader())
            .thenReturn(mediaHttpUploader);
        when(mediaHttpUploader.setDirectUploadEnabled(anyBoolean()))
            .thenReturn(mediaHttpUploader);
        when(mediaHttpUploader.setProgressListener(progressListener))
            .thenReturn(mediaHttpUploader);
        when(insert.execute())
            .thenReturn(video);

        Mono<Video> result = videoSender.sendVideo(youTube, multipartFile, List.of("parts"), video);

        create(result)
            .expectNext(video)
            .verifyComplete();
    }

    @Test
    void testSendVideoWhenExceptionOccurred() throws IOException {
        doThrow(IOException.class)
            .when(multipartFile).getInputStream();

        Mono<Video> result = videoSender.sendVideo(youTube, multipartFile, List.of("parts"), video);

        create(result)
            .expectError(YouTubeUploadException.class)
            .verify();
    }
}
