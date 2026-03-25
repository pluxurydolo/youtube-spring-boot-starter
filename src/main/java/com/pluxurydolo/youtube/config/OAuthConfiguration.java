package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pluxurydolo.youtube.security.CredentialsRetriever;
import com.pluxurydolo.youtube.security.secret.ClientSecretProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class OAuthConfiguration {

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
    public GoogleClientSecrets googleClientSecrets(GsonFactory gsonFactory, ClientSecretProvider youtubeClientSecretProvider) {
        InputStream clientSecret = youtubeClientSecretProvider.getClientSecret();
        InputStreamReader inputStreamReader = new InputStreamReader(clientSecret, UTF_8);

        try {
            return GoogleClientSecrets.load(gsonFactory, inputStreamReader);
        } catch (IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Bean
    public CredentialsRetriever credentialsRetriever(GoogleClientSecrets googleClientSecrets) {
        return new CredentialsRetriever(googleClientSecrets);
    }
}
