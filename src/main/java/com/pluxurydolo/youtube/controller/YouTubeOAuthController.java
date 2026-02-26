package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.service.YouTubeOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class YouTubeOAuthController {
    private final YouTubeOAuthService youTubeOAuthService;

    public YouTubeOAuthController(YouTubeOAuthService youTubeOAuthService) {
        this.youTubeOAuthService = youTubeOAuthService;
    }

    @GetMapping("${youtube.login.url}")
    public Mono<ResponseEntity<Void>> login() {
        return youTubeOAuthService.login();
    }

    @GetMapping("${youtube.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return youTubeOAuthService.callback(code);
    }
}
