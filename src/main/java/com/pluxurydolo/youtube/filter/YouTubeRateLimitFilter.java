package com.pluxurydolo.youtube.filter;

import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.properties.YouTubeRateLimitProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class YouTubeRateLimitFilter implements WebFilter {
    private final AtomicInteger requestCounter;
    private final YouTubeProperties youTubeProperties;
    private final YouTubeRateLimitProperties youTubeRateLimitProperties;

    public YouTubeRateLimitFilter(
        AtomicInteger requestCounter,
        YouTubeProperties youTubeProperties,
        YouTubeRateLimitProperties youTubeRateLimitProperties
    ) {
        this.requestCounter = requestCounter;
        this.youTubeProperties = youTubeProperties;
        this.youTubeRateLimitProperties = youTubeRateLimitProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String loginUrl = youTubeProperties.loginUrl();
        String redirectUrl = youTubeProperties.redirectUrl();
        String refreshUrl = youTubeProperties.refreshUrl();

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (!path.equals(loginUrl) && !path.equals(redirectUrl) && !path.equals(refreshUrl)) {
            return chain.filter(exchange);
        }

        int threshold = youTubeRateLimitProperties.threshold();

        if (requestCounter.incrementAndGet() <= threshold) {
            return handleRequest(exchange, chain);
        } else {
            return dropRequest(exchange);
        }
    }

    private Mono<Void> handleRequest(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
            .doFinally(_ -> requestCounter.decrementAndGet());
    }

    private Mono<Void> dropRequest(ServerWebExchange exchange) {
        requestCounter.decrementAndGet();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(TOO_MANY_REQUESTS);

        return response.setComplete();
    }
}
