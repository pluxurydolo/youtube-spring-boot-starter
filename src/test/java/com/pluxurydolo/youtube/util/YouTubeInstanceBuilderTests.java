package com.pluxurydolo.youtube.util;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.dto.Tokens;
import com.pluxurydolo.youtube.token.YouTubeTokenRefresher;
import com.pluxurydolo.youtube.token.AbstractTokenRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeInstanceBuilderTests {

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private YouTubeTokenRefresher youTubeTokenRefresher;

    @Mock
    private NetHttpTransport netHttpTransport;

    @Mock
    private GsonFactory gsonFactory;

    @Mock
    private HttpCredentialsAdapter httpCredentialsAdapter;

    @InjectMocks
    private YouTubeInstanceBuilder youTubeInstanceBuilder;

    @BeforeEach
    void setUp() {
        setField(youTubeInstanceBuilder, "applicationName", "situational-cringecore");
    }

    @Test
    void testBuild() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(new Tokens("accessToken", "refreshToken")));
        when(youTubeTokenRefresher.refresh(anyString()))
            .thenReturn(Mono.just(httpCredentialsAdapter));

        Mono<YouTube> result = youTubeInstanceBuilder.build();

        create(result)
            .expectNextMatches(youTube -> {
                assertThat(youTube)
                    .usingRecursiveComparison()
                    .isEqualTo(youTube());

                return true;
            })
            .verifyComplete();
    }

    private YouTube youTube() {
        return new YouTube.Builder(netHttpTransport, gsonFactory, httpCredentialsAdapter)
            .setApplicationName("situational-cringecore")
            .build();
    }
}
