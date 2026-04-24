package com.pluxurydolo.youtube.configuration;

import com.pluxurydolo.youtube.step.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import com.pluxurydolo.youtube.step.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeUploadProgressListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeUploadStepConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder() {
        return new YouTubeVideoSnippetBuilder();
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeVideoStatusBuilder youTubeVideoStatusBuilder() {
        return new YouTubeVideoStatusBuilder();
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeVideoBuilder youTubeVideoBuilder() {
        return new YouTubeVideoBuilder();
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeVideoUploader youTubeVideoUploader(YouTubeUploadProgressListener youTubeUploadProgressListener) {
        return new YouTubeVideoUploader(youTubeUploadProgressListener);
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeUploadProgressListener youTubeUploadProgressListener() {
        return new YouTubeUploadProgressListener();
    }
}
