package com.pluxurydolo.youtube.flow.upload;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class YouTubeVideoBuilderTests {
    private static final YouTubeVideoBuilder BUILDER = new YouTubeVideoBuilder();

    @Test
    void testBuild() {
        Video result = BUILDER.build(videoSnippet(), videoStatus());

        assertThat(result)
            .isEqualTo(video());
    }

    private static VideoSnippet videoSnippet() {
        return new VideoSnippet();
    }

    private static VideoStatus videoStatus() {
        return new VideoStatus();
    }

    private static Video video() {
        Video video = new Video();
        video.setSnippet(videoSnippet());
        video.setStatus(videoStatus());
        return video;
    }
}
