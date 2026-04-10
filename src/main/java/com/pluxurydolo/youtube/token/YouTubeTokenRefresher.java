package com.pluxurydolo.youtube.token;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class YouTubeTokenRefresher {
    private final GoogleClientSecrets googleClientSecrets;
    private final AbstractTokenSaver abstractTokenSaver;

    public YouTubeTokenRefresher(GoogleClientSecrets googleClientSecrets, AbstractTokenSaver abstractTokenSaver) {
        this.googleClientSecrets = googleClientSecrets;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<HttpCredentialsAdapter> refresh(String refreshToken) {
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
            .flatMap(response -> abstractTokenSaver.save(response, refreshToken))
            .thenReturn(userCredentials)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
