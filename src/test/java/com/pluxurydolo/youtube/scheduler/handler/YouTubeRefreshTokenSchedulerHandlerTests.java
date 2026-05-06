package com.pluxurydolo.youtube.scheduler.handler;

import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class YouTubeRefreshTokenSchedulerHandlerTests {

    @Mock
    private YouTubeRefreshTokenFlow youTubeRefreshTokenFlow;

    @InjectMocks
    private YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler;

    @Test
    void testHandle() {
        when(youTubeRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = youTubeRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(youTubeRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = youTubeRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}
