package com.pluxurydolo.youtube.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.pluxurydolo.youtube.secret.ClientSecretProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class SecretTestConfig {

    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException {
        GoogleAuthorizationCodeFlow flow = mock(GoogleAuthorizationCodeFlow.class);
        GoogleAuthorizationCodeRequestUrl requestUrl = mock(GoogleAuthorizationCodeRequestUrl.class);
        GoogleAuthorizationCodeTokenRequest tokenRequest = mock(GoogleAuthorizationCodeTokenRequest.class);

        when(flow.newAuthorizationUrl())
            .thenReturn(requestUrl);
        when(requestUrl.setRedirectUri(anyString()))
            .thenReturn(requestUrl);
        when(requestUrl.build())
            .thenReturn("requestUrl");
        when(flow.newTokenRequest(anyString()))
            .thenReturn(tokenRequest);
        when(tokenRequest.setRedirectUri(anyString()))
            .thenReturn(tokenRequest);
        when(tokenRequest.execute())
            .thenReturn(googleTokenResponse());

        return flow;
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

    private static GoogleTokenResponse googleTokenResponse() {
        GoogleTokenResponse googleTokenResponse = new GoogleTokenResponse();
        googleTokenResponse.setAccessToken("accessToken");
        googleTokenResponse.setRefreshToken("refreshToken");
        googleTokenResponse.setExpiresInSeconds(1L);
        googleTokenResponse.setTokenType("tokenType");
        googleTokenResponse.setScope("scope");
        return googleTokenResponse;
    }
}
