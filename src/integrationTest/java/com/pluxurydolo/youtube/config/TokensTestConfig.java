package com.pluxurydolo.youtube.config;

import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TokensTestConfig {

    @Bean
    public AbstractTokenRetriever abstractTokensRetriever() {
        return new AbstractTokenRetriever() {

            @Override
            public Mono<Map<String, String>> retrieveTokens() {
                return Mono.just(
                    Map.of(
                        "exchange_token", "exchangeToken",
                        "access_token", "accessToken"
                    )
                );
            }
        };
    }

    @Bean
    public AbstractTokenSaver abstractTokensSaver() {
        return new AbstractTokenSaver() {

            @Override
            public Mono<String> saveTokens(Map<String, String> tokens) {
                return Mono.just("");
            }
        };
    }

    @Bean
    public YouTubeTokenRefresher youTubeTokenRefresher() {
        YouTubeTokenRefresher youTubeTokenRefresher = mock(YouTubeTokenRefresher.class);
        HttpCredentialsAdapter httpCredentialsAdapter = mock(HttpCredentialsAdapter.class);

        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.just(httpCredentialsAdapter));

        return youTubeTokenRefresher;
    }
}
