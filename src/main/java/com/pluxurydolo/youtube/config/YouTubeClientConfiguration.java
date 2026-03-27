package com.pluxurydolo.youtube.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.client.YouTubeClient;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.security.CredentialsRetriever;
import com.pluxurydolo.youtube.security.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.step.VideoBuilder;
import com.pluxurydolo.youtube.step.VideoSender;
import com.pluxurydolo.youtube.step.VideoSnippetBuilder;
import com.pluxurydolo.youtube.step.VideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeClientConfiguration {

    @Bean
    public YouTubeClient youTubeClient(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        VideoSnippetBuilder videoSnippetBuilder,
        VideoStatusBuilder videoStatusBuilder,
        VideoBuilder videoBuilder,
        VideoSender videoSender
    ) {
        return new YouTubeClient(
            youTubeInstanceBuilder,
            videoSnippetBuilder,
            videoStatusBuilder,
            videoBuilder,
            videoSender
        );
    }

    @Bean
    public YouTubeInstanceBuilder youTubeInstanceBuilder(
        AbstractTokenRetriever abstractYoutubeTokenRetriever,
        CredentialsRetriever credentialsRetriever,
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory,
        YouTubeProperties youTubeProperties
    ) {
        String applicationName = youTubeProperties.applicationName();

        return new YouTubeInstanceBuilder(
            abstractYoutubeTokenRetriever,
            credentialsRetriever,
            netHttpTransport,
            gsonFactory,
            applicationName
        );
    }
}
