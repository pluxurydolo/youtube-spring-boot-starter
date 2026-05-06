package com.pluxurydolo.youtube.configuration;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoUploader;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import com.pluxurydolo.youtube.util.YouTubeUploadProgressListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeUploadConfiguration {

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
    public YouTubeVideoUploader youTubeVideoUploader(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder,
        YouTubeVideoStatusBuilder youTubeVideoStatusBuilder,
        YouTubeVideoBuilder youTubeVideoBuilder,
        MediaHttpUploaderProgressListener progressListener
    ) {
        return new YouTubeVideoUploader(
            youTubeInstanceBuilder,
            youTubeVideoSnippetBuilder,
            youTubeVideoStatusBuilder,
            youTubeVideoBuilder,
            progressListener
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeUploadProgressListener youTubeUploadProgressListener() {
        return new YouTubeUploadProgressListener();
    }
}
