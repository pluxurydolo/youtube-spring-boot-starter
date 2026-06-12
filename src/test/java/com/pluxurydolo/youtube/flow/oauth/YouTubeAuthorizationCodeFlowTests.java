package com.pluxurydolo.youtube.flow.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YouTubeAuthorizationCodeFlowTests {

    @Mock
    private YouTubeAuthProperties youTubeAuthProperties;

    @Mock
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @Mock
    private GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl;

    @InjectMocks
    private YouTubeAuthorizationCodeFlow youTubeAuthorizationCodeFlow;

    @Test
    void testGetResponse() {
        doNothing()
            .when(httpHeaders).setLocation(any());
        when(youTubeAuthProperties.redirectUri())
            .thenReturn("redirectUri");
        when(googleAuthorizationCodeFlow.newAuthorizationUrl())
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.build())
            .thenReturn("");
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.getHeaders())
            .thenReturn(httpHeaders);

        ServerHttpResponse result = youTubeAuthorizationCodeFlow.getResponse(serverWebExchange);

        assertThat(result)
            .isEqualTo(serverHttpResponse);
    }

    @Test
    void testGetResponseWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(serverWebExchange).getResponse();
        when(youTubeAuthProperties.redirectUri())
            .thenReturn("redirectUri");
        when(googleAuthorizationCodeFlow.newAuthorizationUrl())
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.setRedirectUri(anyString()))
            .thenReturn(googleAuthorizationCodeRequestUrl);
        when(googleAuthorizationCodeRequestUrl.build())
            .thenReturn("");

        assertThrows(
            RuntimeException.class,
            () -> youTubeAuthorizationCodeFlow.getResponse(serverWebExchange)
        );
    }
}
