package com.pluxurydolo.youtube.service;

import com.pluxurydolo.youtube.flow.oauth.YouTubeAccessTokenFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeAuthorizationCodeFlow;
import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class YouTubeOAuthService {
    private final YouTubeAuthorizationCodeFlow youTubeAuthorizationCodeFlow;
    private final YouTubeAccessTokenFlow youTubeAccessTokenFlow;
    private final YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    public YouTubeOAuthService(
        YouTubeAuthorizationCodeFlow youTubeAuthorizationCodeFlow,
        YouTubeAccessTokenFlow youTubeAccessTokenFlow,
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow
    ) {
        this.youTubeAuthorizationCodeFlow = youTubeAuthorizationCodeFlow;
        this.youTubeAccessTokenFlow = youTubeAccessTokenFlow;
        this.youTubeRefreshTokenFlow = youTubeRefreshTokenFlow;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        ServerHttpResponse response = youTubeAuthorizationCodeFlow.getResponse(serverWebExchange);
        return response.setComplete();
    }

    public Mono<String> redirect(String code) {
        return youTubeAccessTokenFlow.getAccessToken(code);
    }

    public Mono<String> refreshToken() {
        return youTubeRefreshTokenFlow.refreshToken();
    }
}
