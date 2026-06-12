package com.pluxurydolo.youtube.flow.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.pluxurydolo.youtube.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeAccessTokenFlowTests {

    @Mock
    private YouTubeAuthProperties youTubeAuthProperties;

    @Mock
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @Mock
    private AccessTokenFlowHook accessTokenFlowHook;

    @Mock
    private GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;

    @Mock
    private GoogleTokenResponse googleTokenResponse;

    @InjectMocks
    private YouTubeAccessTokenFlow youTubeAccessTokenFlow;

    @Test
    void testGetAccessToken() throws IOException {
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
        when(accessTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeAccessTokenFlow.getAccessToken("code");

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testGetAccessTokenWhenExceptionOccurred() throws IOException {
        doThrow(RuntimeException.class)
            .when(googleAuthorizationCodeTokenRequest).execute();
        when(youTubeAuthProperties.redirectUri())
            .thenReturn("redirectUri");
        when(googleAuthorizationCodeFlow.newTokenRequest(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(googleAuthorizationCodeTokenRequest.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(accessTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeAccessTokenFlow.getAccessToken("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
