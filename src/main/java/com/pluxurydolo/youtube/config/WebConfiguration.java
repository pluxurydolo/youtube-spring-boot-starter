package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.security.token.AbstractTokenSaver;
import com.pluxurydolo.youtube.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    private final String redirectUri;

    public WebConfiguration(@Value("${youtube.redirect.uri}") String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Bean
    public YouTubeOAuthController oauthController(OAuthService oAuthService) {
        return new YouTubeOAuthController(oAuthService);
    }

    @Bean
    public OAuthService oauthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new OAuthService(googleAuthorizationCodeFlow, abstractTokenSaver, redirectUri);
    }
}
