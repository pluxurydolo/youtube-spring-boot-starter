package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.step.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import com.pluxurydolo.youtube.step.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeUploadProgressListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeUploadStepConfiguration {

    @Bean
    public YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder() {
        return new YouTubeVideoSnippetBuilder();
    }

    @Bean
    public YouTubeVideoStatusBuilder youTubeVideoStatusBuilder() {
        return new YouTubeVideoStatusBuilder();
    }

    @Bean
    public YouTubeVideoBuilder youTubeVideoBuilder() {
        return new YouTubeVideoBuilder();
    }

    @Bean
    public YouTubeVideoUploader youTubeVideoUploader(YouTubeUploadProgressListener youTubeUploadProgressListener) {
        return new YouTubeVideoUploader(youTubeUploadProgressListener);
    }

    @Bean
    public YouTubeUploadProgressListener youTubeUploadProgressListener() {
        return new YouTubeUploadProgressListener();
    }
}
