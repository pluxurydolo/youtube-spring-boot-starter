package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class YouTubeOAuthController {
    private final OAuthService oAuthService;

    public YouTubeOAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("${youtube.login.url}")
    public Mono<ResponseEntity<Void>> login() {
        return oAuthService.login();
    }

    @GetMapping("${youtube.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return oAuthService.callback(code);
    }
}
