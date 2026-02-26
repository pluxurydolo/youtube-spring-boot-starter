package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.client.YouTubeClient;
import com.pluxurydolo.youtube.controller.YouTubeOAuthController;
import com.pluxurydolo.youtube.security.CredentialsRetriever;
import com.pluxurydolo.youtube.security.secret.YouTubeClientSecretProvider;
import com.pluxurydolo.youtube.security.token.AbstractYouTubeTokenRetriever;
import com.pluxurydolo.youtube.security.token.AbstractYouTubeTokenSaver;
import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@AutoConfiguration
@ConditionalOnBean(YouTubeClientSecretProvider.class)
@ConditionalOnProperty(prefix = "youtube", name = "application-name")
@ConditionalOnProperty(prefix = "youtube", name = "redirect.uri")
public class YouTubeAutoConfiguration {
    private final String applicationName;
    private final String redirectUri;

    public YouTubeAutoConfiguration(
        @Value("${youtube.application-name}") String applicationName,
        @Value("${youtube.redirect.uri}") String redirectUri
    ) {
        this.applicationName = applicationName;
        this.redirectUri = redirectUri;
    }

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
    public GoogleClientSecrets googleClientSecrets(GsonFactory gsonFactory, YouTubeClientSecretProvider youtubeClientSecretProvider) {
        InputStream clientSecret = youtubeClientSecretProvider.getClientSecret();
        InputStreamReader inputStreamReader = new InputStreamReader(clientSecret);

        try {
            return GoogleClientSecrets.load(gsonFactory, inputStreamReader);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Bean
    public NetHttpTransport netHttpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public GsonFactory gsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    public YouTubeClient youTubeClient(YouTubeInstanceBuilder youTubeInstanceBuilder) {
        return new YouTubeClient(youTubeInstanceBuilder);
    }

    @Bean
    public YouTubeInstanceBuilder youTubeInstanceBuilder(
        AbstractYouTubeTokenRetriever abstractYoutubeTokenRetriever,
        CredentialsRetriever credentialsRetriever,
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory
    ) {
        return new YouTubeInstanceBuilder(
            abstractYoutubeTokenRetriever,
            credentialsRetriever,
            netHttpTransport,
            gsonFactory,
            applicationName
        );
    }

    @Bean
    public YouTubeOAuthController oauthController(YouTubeOAuthService youTubeOAuthService) {
        return new YouTubeOAuthController(youTubeOAuthService);
    }

    @Bean
    public YouTubeOAuthService oauthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractYouTubeTokenSaver abstractYouTubeTokenSaver
    ) {
        return new YouTubeOAuthService(googleAuthorizationCodeFlow, abstractYouTubeTokenSaver, redirectUri);
    }

    @Bean
    public CredentialsRetriever credentialsRetriever(GoogleClientSecrets googleClientSecrets) {
        return new CredentialsRetriever(googleClientSecrets);
    }
}
