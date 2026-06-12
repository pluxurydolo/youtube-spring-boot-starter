package com.pluxurydolo.youtube.configuration;

import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.flow.oauth.YouTubeAccessTokenFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeAuthorizationCodeFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.service.YouTubeOAuthService;
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
        YouTubeAuthorizationCodeFlow youTubeAuthorizationCodeFlow,
        YouTubeAccessTokenFlow youTubeAccessTokenFlow,
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow
    ) {
        return new YouTubeOAuthService(youTubeAuthorizationCodeFlow, youTubeAccessTokenFlow, youTubeRefreshTokenFlow);
    }
}
