package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.flow.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeWebConfiguration {

    @Bean
    public YouTubeOAuthController youTubeOAuthController(YouTubeOAuthService youTubeOAuthService) {
        return new YouTubeOAuthController(youTubeOAuthService);
    }

    @Bean
    public YouTubeOAuthService youTubeOAuthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow,
        AbstractTokenSaver abstractTokenSaver,
        YouTubeProperties youTubeProperties
    ) {
        return new YouTubeOAuthService(
            googleAuthorizationCodeFlow,
            abstractTokenSaver,
            youTubeRefreshTokenFlow,
            youTubeProperties
        );
    }
}
