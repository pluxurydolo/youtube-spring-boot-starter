package com.pluxurydolo.youtube.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.client.YouTubeClient;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.step.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import com.pluxurydolo.youtube.step.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeClientConfiguration {

    @Bean
    public YouTubeClient youTubeClient(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder,
        YouTubeVideoStatusBuilder youTubeVideoStatusBuilder,
        YouTubeVideoBuilder youTubeVideoBuilder,
        YouTubeVideoUploader youTubeVideoUploader
    ) {
        return new YouTubeClient(
            youTubeInstanceBuilder,
            youTubeVideoSnippetBuilder,
            youTubeVideoStatusBuilder,
            youTubeVideoBuilder,
            youTubeVideoUploader
        );
    }

    @Bean
    public YouTubeInstanceBuilder youTubeInstanceBuilder(
        AbstractTokenRetriever abstractTokenRetriever,
        YouTubeTokenRefresher youTubeTokenRefresher,
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory,
        YouTubeProperties youTubeProperties
    ) {
        String applicationName = youTubeProperties.applicationName();

        return new YouTubeInstanceBuilder(
            abstractTokenRetriever,
            youTubeTokenRefresher,
            netHttpTransport,
            gsonFactory,
            applicationName
        );
    }
}
