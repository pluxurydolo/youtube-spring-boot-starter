package com.pluxurydolo.youtube.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "youtube.rate-limit")
public record YouTubeRateLimitProperties(int threshold) {
}
