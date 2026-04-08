package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.pluxurydolo.youtube.secret.ClientSecretProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class SecretTestConfig {

    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() {
        return mock(GoogleAuthorizationCodeFlow.class);
    }

    @Bean
    public GoogleClientSecrets googleClientSecrets() {
        GoogleClientSecrets googleClientSecrets = mock(GoogleClientSecrets.class);
        GoogleClientSecrets.Details details = mock(GoogleClientSecrets.Details.class);

        when(googleClientSecrets.getDetails())
            .thenReturn(details);
        when(details.getClientId())
            .thenReturn("clientId");
        when(details.getClientSecret())
            .thenReturn("clientSecret");

        return googleClientSecrets;
    }

    @Bean
    public ClientSecretProvider clientSecretProvider() {
        return () -> null;
    }
}
