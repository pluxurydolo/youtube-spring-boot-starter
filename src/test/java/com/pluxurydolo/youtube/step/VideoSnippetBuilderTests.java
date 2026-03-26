package com.pluxurydolo.youtube.step;

import com.google.api.services.youtube.model.VideoSnippet;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class VideoSnippetBuilderTests {
    private static final VideoSnippetBuilder BUILDER = new VideoSnippetBuilder();

    @Test
    void testBuild() {
        VideoSnippet result = BUILDER.build("title", "description", new String[]{"tag1", "tag2"});

        assertThat(result)
            .isEqualTo(videoSnippet());
    }

    private static VideoSnippet videoSnippet() {
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle("title");
        snippet.setDescription("description");
        snippet.setCategoryId("24");
        snippet.setTags(asList("tag1", "tag2"));
        return snippet;
    }
}
