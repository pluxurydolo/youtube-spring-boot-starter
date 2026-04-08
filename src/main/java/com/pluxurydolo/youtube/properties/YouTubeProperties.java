package com.pluxurydolo.youtube.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "youtube")
public record YouTubeProperties(
    String applicationName,

    @Name("redirect.uri")
    String redirectUri,

    @Name("login.url")
    String loginUrl,

    @Name("redirect.url")
    String redirectUrl,

    @Name("refresh.url")
    String refreshUrl
) {
}
