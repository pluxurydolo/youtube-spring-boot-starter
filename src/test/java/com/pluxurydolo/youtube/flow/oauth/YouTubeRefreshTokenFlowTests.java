package com.pluxurydolo.youtube.flow.oauth;

import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.dto.YouTubeTokens;
import com.pluxurydolo.youtube.exception.YouTubeRefreshTokenException;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeRefreshTokenFlowTests {

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private YouTubeTokenRefresher youTubeTokenRefresher;

    @Mock
    private HttpCredentialsAdapter httpCredentialsAdapter;

    @Mock
    private Credentials credentials;

    @InjectMocks
    private YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(youTubeTokens()));
        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.just(httpCredentialsAdapter));
        when(httpCredentialsAdapter.getCredentials())
            .thenReturn(credentials);
        when(credentials.getAuthenticationType())
            .thenReturn("authenticationType");

        Mono<String> result = youTubeRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("authenticationType")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = youTubeRefreshTokenFlow.refreshToken();

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(YouTubeRefreshTokenException.class));
    }

    private static YouTubeTokens youTubeTokens() {
        return new YouTubeTokens("accessToken", "refreshToken");
    }
}
