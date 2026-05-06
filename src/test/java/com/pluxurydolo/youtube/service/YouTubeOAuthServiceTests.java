package com.pluxurydolo.youtube.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeOAuthServiceTests {

    @Mock
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @Mock
    private YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    @Mock
    private YouTubeAuthProperties youTubeAuthProperties;

    @Mock
    private GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl;

    @Mock
    private GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;

    @Mock
    private GoogleTokenResponse googleTokenResponse;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private YouTubeOAuthService youTubeOAuthService;

    @Test
    void testLogin() {
        doNothing()
            .when(httpHeaders).setLocation(any());
        when(youTubeAuthProperties.redirectUri())
            .thenReturn("redirectUri");
        when(googleAuthorizationCodeFlow.newAuthorizationUrl())
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.build())
            .thenReturn("authorizationUrl");
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.getHeaders())
            .thenReturn(httpHeaders);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = youTubeOAuthService.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() throws IOException {
        when(youTubeAuthProperties.redirectUri())
            .thenReturn("redirectUri");
        when(googleAuthorizationCodeFlow.newTokenRequest(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(googleAuthorizationCodeTokenRequest.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(googleAuthorizationCodeTokenRequest.execute())
            .thenReturn(googleTokenResponse);
        when(abstractTokenSaver.save(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeOAuthService.redirect("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(youTubeRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
