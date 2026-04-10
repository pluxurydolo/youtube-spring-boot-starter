package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.flow.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.secret.ClientSecretProvider;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class YouTubeOAuthConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeOAuthConfiguration.class);

    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow(
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory,
        GoogleClientSecrets googleClientSecrets
    ) {
        List<String> scopes = Arrays.asList("https://www.googleapis.com/auth/youtube.upload");

        return new GoogleAuthorizationCodeFlow.Builder(netHttpTransport, gsonFactory, googleClientSecrets, scopes)
            .setAccessType("offline")
            .setApprovalPrompt("force")
            .build();
    }

    @Bean
    public GoogleClientSecrets googleClientSecrets(GsonFactory gsonFactory, ClientSecretProvider clientSecretProvider) {
        InputStream clientSecret = clientSecretProvider.getClientSecret();
        InputStreamReader inputStreamReader = new InputStreamReader(clientSecret, UTF_8);

        try {
            return GoogleClientSecrets.load(gsonFactory, inputStreamReader);
        } catch (IOException exception) {
            LOGGER.error("gxil [youtube-starter] Произошла ошибка при загрузке GoogleClientSecrets");
            throw new IllegalStateException(exception);
        }
    }

    @Bean
    public YouTubeTokenRefresher youTubeTokenRefresher(
        GoogleClientSecrets googleClientSecrets,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new YouTubeTokenRefresher(googleClientSecrets, abstractTokenSaver);
    }

    @Bean
    public YouTubeRefreshTokenFlow youTubeRefreshTokenFlow(
        AbstractTokenRetriever abstractTokenRetriever,
        YouTubeTokenRefresher youTubeTokenRefresher
    ) {
        return new YouTubeRefreshTokenFlow(abstractTokenRetriever, youTubeTokenRefresher);
    }
}
