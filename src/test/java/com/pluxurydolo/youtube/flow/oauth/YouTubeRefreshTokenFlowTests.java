package com.pluxurydolo.youtube.flow.oauth;

import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.dto.YouTubeTokens;
import com.pluxurydolo.youtube.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
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
    private RefreshTokenFlowHook refreshTokenFlowHook;

    @Mock
    private HttpCredentialsAdapter httpCredentialsAdapter;

    @InjectMocks
    private YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(youTubeTokens()));
        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.just(httpCredentialsAdapter));
        when(refreshTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(youTubeTokens()));
        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(refreshTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static YouTubeTokens youTubeTokens() {
        return new YouTubeTokens("accessToken", "refreshToken");
    }
}
