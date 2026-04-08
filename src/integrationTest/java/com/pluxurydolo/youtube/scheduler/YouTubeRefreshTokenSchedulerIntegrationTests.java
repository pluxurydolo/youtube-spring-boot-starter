package com.pluxurydolo.youtube.scheduler;

import com.pluxurydolo.youtube.base.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class YouTubeRefreshTokenSchedulerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private YouTubeRefreshTokenScheduler scheduler;

    @Test
    void testSchedule() {
        assertDoesNotThrow(scheduler::schedule);
    }
}
