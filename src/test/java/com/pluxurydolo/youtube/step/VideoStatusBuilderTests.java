package com.pluxurydolo.youtube.step;

import com.google.api.services.youtube.model.VideoStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoStatusBuilderTests {
    private static final VideoStatusBuilder BUILDER = new VideoStatusBuilder();

    @Test
    void testBuild() {
        VideoStatus result = BUILDER.build();

        assertThat(result)
            .isEqualTo(videoStatus());
    }

    private static VideoStatus videoStatus() {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("public");
        status.setSelfDeclaredMadeForKids(false);
        status.setEmbeddable(true);
        return status;
    }
}
