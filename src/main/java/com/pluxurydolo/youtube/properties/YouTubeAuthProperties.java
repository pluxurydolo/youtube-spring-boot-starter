package com.pluxurydolo.youtube.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "youtube.auth")
public record YouTubeAuthProperties(
    @Name("application-name") String applicationName,
    @Name("redirect-uri") String redirectUri
) {
}
