package com.pluxurydolo.youtube.step;

import com.google.api.services.youtube.model.VideoSnippet;

import static java.util.Arrays.asList;

public class VideoSnippetBuilder {
    public VideoSnippet build(String title, String description, String[] tags) {
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setCategoryId("24");
        snippet.setTags(asList(tags));
        return snippet;
    }
}
