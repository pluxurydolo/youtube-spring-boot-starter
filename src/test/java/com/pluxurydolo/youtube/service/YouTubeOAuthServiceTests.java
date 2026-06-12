package com.pluxurydolo.youtube.service;

import com.pluxurydolo.youtube.flow.oauth.YouTubeAccessTokenFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeAuthorizationCodeFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeOAuthServiceTests {

    @Mock
    private YouTubeAuthorizationCodeFlow youTubeAuthorizationCodeFlow;

    @Mock
    private YouTubeAccessTokenFlow youTubeAccessTokenFlow;

    @Mock
    private YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @InjectMocks
    private YouTubeOAuthService youTubeOAuthService;

    @Test
    void testLogin() {
        when(youTubeAuthorizationCodeFlow.getResponse(any()))
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = youTubeOAuthService.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() {
        when(youTubeAccessTokenFlow.getAccessToken(anyString()))
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
