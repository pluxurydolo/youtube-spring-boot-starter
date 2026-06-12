package com.pluxurydolo.youtube.flow.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.pluxurydolo.youtube.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class YouTubeAccessTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(YouTubeAccessTokenFlow.class);

    private final YouTubeAuthProperties youTubeAuthProperties;
    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
    private final AbstractTokenSaver abstractTokenSaver;
    private final AccessTokenFlowHook accessTokenFlowHook;

    public YouTubeAccessTokenFlow(
        YouTubeAuthProperties youTubeAuthProperties,
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow,
        AbstractTokenSaver abstractTokenSaver,
        AccessTokenFlowHook accessTokenFlowHook
    ) {
        this.youTubeAuthProperties = youTubeAuthProperties;
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
        this.abstractTokenSaver = abstractTokenSaver;
        this.accessTokenFlowHook = accessTokenFlowHook;
    }

    public Mono<String> getAccessToken(String code) {
        String redirectUri = youTubeAuthProperties.redirectUri();

        GoogleAuthorizationCodeTokenRequest tokenRequest = googleAuthorizationCodeFlow.newTokenRequest(code)
            .setRedirectUri(redirectUri);

        return Mono.fromCallable(tokenRequest::execute)
            .flatMap(abstractTokenSaver::save)
            .flatMap(_ -> accessTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("hssd [youtube-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("anbc [youtube-starter] Произошла ошибка при получении access token");
                return accessTokenFlowHook.handleException(throwable);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
