package com.pluxurydolo.youtube.flow.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FOUND;

public class YouTubeAuthorizationCodeFlow {
    private final YouTubeAuthProperties youTubeAuthProperties;
    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    public YouTubeAuthorizationCodeFlow(
        YouTubeAuthProperties youTubeAuthProperties,
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow
    ) {
        this.youTubeAuthProperties = youTubeAuthProperties;
        this.googleAuthorizationCodeFlow = googleAuthorizationCodeFlow;
    }

    public ServerHttpResponse getResponse(ServerWebExchange serverWebExchange) {
        URI authorizationUri = getAuthorizationUri();

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FOUND);
        response.getHeaders().setLocation(authorizationUri);

        return response;
    }

    private URI getAuthorizationUri() {
        String redirectUri = youTubeAuthProperties.redirectUri();

        String authorizationUrl = googleAuthorizationCodeFlow.newAuthorizationUrl()
            .setRedirectUri(redirectUri)
            .build();

        return create(authorizationUrl);
    }
}
