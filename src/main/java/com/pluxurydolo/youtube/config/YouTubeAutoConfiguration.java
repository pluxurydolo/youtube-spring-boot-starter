package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.properties.YouTubeProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(YouTubeProperties.class)
@Import({
    YouTubeCoreConfiguration.class,
    YouTubeOAuthConfiguration.class,
    YouTubeWebConfiguration.class,
    YouTubeClientConfiguration.class,
    YouTubeUploadStepConfiguration.class,
    YouTubeSchedulingConfiguration.class,
    YouTubeFilterConfiguration.class
})
public class YouTubeAutoConfiguration {
}
