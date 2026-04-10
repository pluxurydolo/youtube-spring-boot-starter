package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class YouTubeOAuthController {
    private final YouTubeOAuthService youTubeOAuthService;

    public YouTubeOAuthController(YouTubeOAuthService youTubeOAuthService) {
        this.youTubeOAuthService = youTubeOAuthService;
    }

    @GetMapping("${youtube.login.url}")
    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        return youTubeOAuthService.login(serverWebExchange);
    }

    @GetMapping("${youtube.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return youTubeOAuthService.callback(code);
    }

    @GetMapping("${youtube.refresh.url}")
    public Mono<String> refreshToken() {
        return youTubeOAuthService.refreshToken();
    }
}
