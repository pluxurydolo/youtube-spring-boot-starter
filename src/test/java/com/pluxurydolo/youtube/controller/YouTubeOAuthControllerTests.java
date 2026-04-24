package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeOAuthControllerTests {

    @Mock
    private YouTubeOAuthService youTubeOAuthService;

    @Mock
    private ServerWebExchange serverWebExchange;

    @InjectMocks
    private YouTubeOAuthController youTubeOAuthController;

    @Test
    void testLogin() {
        when(youTubeOAuthService.login(serverWebExchange))
            .thenReturn(Mono.empty());

        Mono<Void> result = youTubeOAuthController.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() {
        when(youTubeOAuthService.redirect(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeOAuthController.redirect("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(youTubeOAuthService.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeOAuthController.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
