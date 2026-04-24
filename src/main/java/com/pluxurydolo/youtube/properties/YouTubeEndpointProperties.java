package com.pluxurydolo.youtube.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "youtube.endpoint")
public record YouTubeEndpointProperties(

    @Name("login")
    String loginUrl,

    @Name("redirect")
    String redirectUrl,

    @Name("refresh-token")
    String refreshTokenUrl
) {
}
