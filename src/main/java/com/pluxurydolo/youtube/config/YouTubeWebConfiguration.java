package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.security.token.AbstractTokenSaver;
import com.pluxurydolo.youtube.service.OAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeWebConfiguration {

    @Bean
    public YouTubeOAuthController youTubeOAuthController(OAuthService oAuthService) {
        return new YouTubeOAuthController(oAuthService);
    }

    @Bean
    public OAuthService oauthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractTokenSaver abstractTokenSaver,
        YouTubeProperties youTubeProperties
    ) {
        String redirectUri = youTubeProperties.redirectUri();
        return new OAuthService(googleAuthorizationCodeFlow, abstractTokenSaver, redirectUri);
    }
}
