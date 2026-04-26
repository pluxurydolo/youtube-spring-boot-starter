package com.pluxurydolo.youtube.configuration;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class YouTubeTestConfiguration {

    @Bean
    public YouTubeInstanceBuilder youTubeInstanceBuilder() throws IOException {
        YouTubeInstanceBuilder youTubeInstanceBuilder = mock(YouTubeInstanceBuilder.class);
        YouTube youTube = mock(YouTube.class);
        YouTube.Videos videos = mock(YouTube.Videos.class);
        YouTube.Videos.Insert insert = mock(YouTube.Videos.Insert.class);
        MediaHttpUploader uploader = mock(MediaHttpUploader.class);
        Video video = mock(Video.class);

        when(youTubeInstanceBuilder.build())
            .thenReturn(Mono.just(youTube));
        when(youTube.videos())
            .thenReturn(videos);
        when(videos.insert(anyList(), any(), any()))
            .thenReturn(insert);
        when(insert.getMediaHttpUploader())
            .thenReturn(uploader);
        when(uploader.setDirectUploadEnabled(anyBoolean()))
            .thenReturn(uploader);
        when(uploader.setProgressListener(any()))
            .thenReturn(uploader);
        when(insert.execute())
            .thenReturn(video);

        return youTubeInstanceBuilder;
    }
}
