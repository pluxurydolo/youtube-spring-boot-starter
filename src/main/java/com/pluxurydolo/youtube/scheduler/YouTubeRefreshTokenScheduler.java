package com.pluxurydolo.youtube.scheduler;

import com.pluxurydolo.youtube.scheduler.handler.YouTubeRefreshTokenSchedulerHandler;
import org.springframework.scheduling.annotation.Scheduled;

public class YouTubeRefreshTokenScheduler {
    private final YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler;

    public YouTubeRefreshTokenScheduler(YouTubeRefreshTokenSchedulerHandler youTubeRefreshTokenSchedulerHandler) {
        this.youTubeRefreshTokenSchedulerHandler = youTubeRefreshTokenSchedulerHandler;
    }

    @Scheduled(
        cron = "${youtube.refresh.token.scheduler.cron}",
        zone = "${youtube.refresh.token.scheduler.zone}"
    )
    public void schedule() {
        String jobName = getClass().getName();

        youTubeRefreshTokenSchedulerHandler.handle(jobName)
            .subscribe();
    }
}
