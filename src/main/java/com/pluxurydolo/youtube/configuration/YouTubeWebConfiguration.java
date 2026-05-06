package com.pluxurydolo.youtube.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeWebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeOAuthController youTubeOAuthController(YouTubeOAuthService youTubeOAuthService) {
        return new YouTubeOAuthController(youTubeOAuthService);
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeOAuthService youTubeOAuthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow,
        AbstractTokenSaver abstractTokenSaver,
        YouTubeAuthProperties youTubeAuthProperties
    ) {
        return new YouTubeOAuthService(
            googleAuthorizationCodeFlow,
            abstractTokenSaver,
            youTubeRefreshTokenFlow,
            youTubeAuthProperties
        );
    }
}
