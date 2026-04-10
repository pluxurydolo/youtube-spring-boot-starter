package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.flow.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.scheduler.YouTubeRefreshTokenScheduler;
import com.pluxurydolo.youtube.scheduler.handler.YouTubeRefreshTokenSchedulerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class YouTubeSchedulingConfiguration {

    @Bean
    public YouTubeRefreshTokenScheduler youTubeRefreshTokenScheduler(
        YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler
    ) {
        return new YouTubeRefreshTokenScheduler(youTubeRefreshTokenSchedulerHandler);
    }

    @Bean
    public YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler(
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow
    ) {
        return new YouTubeRefreshTokenSchedulerHandler(youTubeRefreshTokenFlow);
    }
}
