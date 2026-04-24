package com.pluxurydolo.youtube.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.pluxurydolo.youtube.flow.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FOUND;

public class YouTubeOAuthService {
    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
    private final AbstractTokenSaver abstractTokenSaver;
    private final YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;
    private final YouTubeAuthProperties youTubeAuthProperties;

    public YouTubeOAuthService(
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractTokenSaver abstractTokenSaver,
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow,
        YouTubeAuthProperties youTubeAuthProperties
    ) {
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.abstractTokenSaver = abstractTokenSaver;
        this.youTubeRefreshTokenFlow = youTubeRefreshTokenFlow;
        this.youTubeAuthProperties = youTubeAuthProperties;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        String redirectUri = youTubeAuthProperties.redirectUri();

        String authorizationUrl = googleAuthorizationCodeFlow.newAuthorizationUrl()
            .setRedirectUri(redirectUri)
            .build();

        URI uri = create(authorizationUrl);

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FOUND);
        response.getHeaders().setLocation(uri);

        return response.setComplete();
    }

    public Mono<String> redirect(String code) {
        String redirectUri = youTubeAuthProperties.redirectUri();

        GoogleAuthorizationCodeTokenRequest tokenRequest = googleAuthorizationCodeFlow.newTokenRequest(code)
            .setRedirectUri(redirectUri);

        return Mono.fromCallable(tokenRequest::execute)
            .flatMap(abstractTokenSaver::save)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> refreshToken() {
        return youTubeRefreshTokenFlow.refreshToken();
    }
}
