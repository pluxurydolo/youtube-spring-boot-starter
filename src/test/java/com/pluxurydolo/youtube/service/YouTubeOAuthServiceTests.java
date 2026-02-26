package com.pluxurydolo.youtube.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.pluxurydolo.youtube.security.token.AbstractYouTubeTokenSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeOAuthServiceTests {

    @Mock
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    @Mock
    private AbstractYouTubeTokenSaver abstractYouTubeTokenSaver;

    @Mock
    private GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl;

    @Mock
    private GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest;

    @Mock
    private GoogleTokenResponse googleTokenResponse;

    @InjectMocks
    private YouTubeOAuthService youTubeOAuthService;

    @BeforeEach
    void setUp() {
        setField(youTubeOAuthService, "redirectUri", "redirectUri");
    }

    @Test
    void testLogin() {
        when(googleAuthorizationCodeFlow.newAuthorizationUrl())
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.build())
            .thenReturn("redirectUrl");

        Mono<ResponseEntity<Void>> result = youTubeOAuthService.login();

        create(result)
            .expectNext(responseEntity())
            .verifyComplete();
    }

    @Test
    void testCallback() throws IOException {
        when(googleAuthorizationCodeFlow.newTokenRequest(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(googleAuthorizationCodeTokenRequest.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeTokenRequest);
        when(googleAuthorizationCodeTokenRequest.execute())
            .thenReturn(googleTokenResponse);
        when(abstractYouTubeTokenSaver.save(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeOAuthService.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static ResponseEntity<Void> responseEntity() {
        URI uri = URI.create("redirectUrl");

        return ResponseEntity.status(FOUND)
            .location(uri)
            .build();
    }
}
