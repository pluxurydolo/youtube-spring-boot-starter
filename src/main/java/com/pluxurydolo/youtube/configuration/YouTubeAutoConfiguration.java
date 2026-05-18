package com.pluxurydolo.youtube.configuration;

import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(YouTubeAuthProperties.class)
@Import({
    YouTubeCoreConfiguration.class,
    YouTubeOAuthConfiguration.class,
    YouTubeWebConfiguration.class,
    YouTubeClientConfiguration.class,
    YouTubeUploadConfiguration.class,
    YouTubeSchedulingConfiguration.class,
    YouTubeResilienceConfiguration.class
})
public class YouTubeAutoConfiguration {
}
