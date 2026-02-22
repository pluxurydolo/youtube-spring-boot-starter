package com.pluxurydolo.youtube.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.pluxurydolo.youtube.security.token.AbstractTokenSaver;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

public class OAuthService {
    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
    private final AbstractTokenSaver abstractTokenSaver;
    private final String redirectUri;

    public OAuthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractTokenSaver abstractTokenSaver,
        String redirectUri
    ) {
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.abstractTokenSaver = abstractTokenSaver;
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
            .flatMap(abstractTokenSaver::save)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
