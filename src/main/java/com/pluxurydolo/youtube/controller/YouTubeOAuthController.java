package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.resilience.annotation.ConcurrencyLimit.ThrottlePolicy.REJECT;

@RestController
public class YouTubeOAuthController {
    private final YouTubeOAuthService youTubeOAuthService;

    public YouTubeOAuthController(YouTubeOAuthService youTubeOAuthService) {
        this.youTubeOAuthService = youTubeOAuthService;
    }

    @GetMapping("${youtube.endpoint.login}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        return youTubeOAuthService.login(serverWebExchange);
    }

    @GetMapping("${youtube.endpoint.redirect}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<String> redirect(@RequestParam("code") String code) {
        return youTubeOAuthService.redirect(code);
    }

    @GetMapping("${youtube.endpoint.refresh-token}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<String> refreshToken() {
        return youTubeOAuthService.refreshToken();
    }
}
