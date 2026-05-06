package com.pluxurydolo.youtube.scheduler;

import com.pluxurydolo.youtube.scheduler.handler.YouTubeRefreshTokenSchedulerHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YouTubeRefreshTokenSchedulerTests {

    @Mock
    private YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler;

    @InjectMocks
    private YouTubeRefreshTokenScheduler youTubeRefreshTokenScheduler;

    @Test
    void testSchedule() {
        when(youTubeRefreshTokenSchedulerHandler.handle(anyString()))
            .thenReturn(Mono.just(""));

        assertDoesNotThrow(youTubeRefreshTokenScheduler::schedule);
    }
}
