package com.pluxurydolo.youtube.config;

import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import com.pluxurydolo.youtube.token.AbstractTokenSaver;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.util.Map;

import static java.time.Clock.systemUTC;
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
                        "refresh_token", "refreshToken",
                        "access_token", "accessToken"
                    )
                );
            }
        };
    }

    @Bean
    public AbstractTokenSaver abstractTokensSaver() {
        return new AbstractTokenSaver(systemUTC()) {

            @Override
            public Mono<String> saveTokens(Map<String, String> tokens) {
                return Mono.just("saveTokens");
            }
        };
    }

    @Bean
    public YouTubeTokenRefresher youTubeTokenRefresher() {
        YouTubeTokenRefresher youTubeTokenRefresher = mock(YouTubeTokenRefresher.class);
        HttpCredentialsAdapter httpCredentialsAdapter = mock(HttpCredentialsAdapter.class);
        Credentials credentials = mock(Credentials.class);

        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.just(httpCredentialsAdapter));
        when(httpCredentialsAdapter.getCredentials())
            .thenReturn(credentials);
        when(credentials.getAuthenticationType())
            .thenReturn("authenticationType");

        return youTubeTokenRefresher;
    }

    @Bean
    public Clock clock() {
        return systemUTC();
    }
}
