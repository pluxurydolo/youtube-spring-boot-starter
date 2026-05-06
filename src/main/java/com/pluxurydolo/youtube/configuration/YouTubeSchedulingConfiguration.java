package com.pluxurydolo.youtube.configuration;

import com.pluxurydolo.youtube.flow.oauth.YouTubeRefreshTokenFlow;
import com.pluxurydolo.youtube.scheduler.YouTubeRefreshTokenScheduler;
import com.pluxurydolo.youtube.scheduler.handler.YouTubeRefreshTokenSchedulerHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class YouTubeSchedulingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeRefreshTokenScheduler youTubeRefreshTokenScheduler(
        YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler
    ) {
        return new YouTubeRefreshTokenScheduler(youTubeRefreshTokenSchedulerHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler(
        YouTubeRefreshTokenFlow youTubeRefreshTokenFlow
    ) {
        return new YouTubeRefreshTokenSchedulerHandler(youTubeRefreshTokenFlow);
    }
}
