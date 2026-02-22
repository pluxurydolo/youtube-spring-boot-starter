package com.pluxurydolo.youtube.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class CredentialsRetriever {
    private final GoogleClientSecrets googleClientSecrets;

    public CredentialsRetriever(GoogleClientSecrets googleClientSecrets) {
        this.googleClientSecrets = googleClientSecrets;
    }

    public Mono<HttpCredentialsAdapter> retrieve(String refreshToken) {
        return userCredentials(refreshToken)
            .map(HttpCredentialsAdapter::new);
    }

    private Mono<UserCredentials> userCredentials(String refreshToken) {
        GoogleClientSecrets.Details secretsDetails = googleClientSecrets.getDetails();
        String clientId = secretsDetails.getClientId();
        String clientSecret = secretsDetails.getClientSecret();

        UserCredentials userCredentials = UserCredentials.newBuilder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRefreshToken(refreshToken)
            .build();

        return Mono.fromCallable(userCredentials::refreshAccessToken)
            .thenReturn(userCredentials)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
