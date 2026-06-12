package com.pluxurydolo.youtube.configuration;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.client.YouTubeVideoClient;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.flow.upload.YouTubeVideoPublisher;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeVideoClient youTubeVideoClient(YouTubeVideoPublisher youTubeVideoPublisher) {
        return new YouTubeVideoClient(youTubeVideoPublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeInstanceBuilder youTubeInstanceBuilder(
        AbstractTokenRetriever abstractTokenRetriever,
        YouTubeTokenRefresher youTubeTokenRefresher,
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory,
        YouTubeAuthProperties youTubeAuthProperties
    ) {
        String applicationName = youTubeAuthProperties.applicationName();

        return new YouTubeInstanceBuilder(
            abstractTokenRetriever,
            youTubeTokenRefresher,
            netHttpTransport,
            gsonFactory,
            applicationName
        );
    }
}
