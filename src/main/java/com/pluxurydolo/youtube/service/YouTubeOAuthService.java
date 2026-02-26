package com.pluxurydolo.youtube.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.pluxurydolo.youtube.security.token.AbstractYouTubeTokenSaver;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

public class YouTubeOAuthService {
    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
    private final AbstractYouTubeTokenSaver abstractYouTubeTokenSaver;
    private final String redirectUri;

    public YouTubeOAuthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractYouTubeTokenSaver abstractYouTubeTokenSaver,
        String redirectUri
    ) {
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.abstractYouTubeTokenSaver = abstractYouTubeTokenSaver;
        this.redirectUri = redirectUri;
    }

    public Mono<ResponseEntity<Void>> login() {
        String authUrl = googleAuthorizationCodeFlow.newAuthorizationUrl()
            .setRedirectUri(redirectUri)
            .build();

        URI uri = URI.create(authUrl);

        ResponseEntity<Void> responseEntity = ResponseEntity.status(FOUND)
            .location(uri)
            .build();

        return Mono.just(responseEntity);
    }

    public Mono<String> callback(String code) {
        GoogleAuthorizationCodeTokenRequest tokenRequest = googleAuthorizationCodeFlow.newTokenRequest(code)
            .setRedirectUri(redirectUri);

        return Mono.fromCallable(tokenRequest::execute)
            .flatMap(abstractYouTubeTokenSaver::save)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
