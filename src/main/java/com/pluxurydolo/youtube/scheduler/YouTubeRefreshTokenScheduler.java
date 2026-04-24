package com.pluxurydolo.youtube.scheduler;

import com.pluxurydolo.youtube.scheduler.handler.YouTubeRefreshTokenSchedulerHandler;
import org.springframework.scheduling.annotation.Scheduled;

public class YouTubeRefreshTokenScheduler {
    private final YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler;

    public YouTubeRefreshTokenScheduler(YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler) {
        this.youTubeRefreshTokenSchedulerHandler = youTubeRefreshTokenSchedulerHandler;
    }

    @Scheduled(
        cron = "${youtube.scheduler.refresh-token.cron}",
        zone = "${youtube.scheduler.refresh-token.zone}"
    )
    public void schedule() {
        String jobName = getClass().getName();

        youTubeRefreshTokenSchedulerHandler.handle(jobName)
            .subscribe();
    }
}
