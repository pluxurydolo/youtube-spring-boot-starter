package com.pluxurydolo.youtube.filter;

import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.validator.RequestParamValidator;
import com.pluxurydolo.youtube.validator.ValidationResult;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.youtube.validator.ValidationResult.SUCCESS;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Order(HIGHEST_PRECEDENCE)
public class YouTubeRequestParamValidationFilter implements WebFilter {
    private final RequestParamValidator requestParamValidator;
    private final YouTubeProperties youTubeProperties;

    public YouTubeRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        YouTubeProperties youTubeProperties
    ) {
        this.requestParamValidator = requestParamValidator;
        this.youTubeProperties = youTubeProperties;
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

        String accessToken = request.getQueryParams().getFirst("access_token");

        return requestParamValidator.validate(accessToken)
            .flatMap(result -> handleValidationResult(exchange, chain, result));
    }

    private static Mono<Void> handleValidationResult(
        ServerWebExchange serverWebExchange,
        WebFilterChain webFilterChain,
        ValidationResult validationResult
    ) {
        if (validationResult == SUCCESS) {
            return webFilterChain.filter(serverWebExchange);
        }

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FORBIDDEN);
        return response.setComplete();
    }
}
