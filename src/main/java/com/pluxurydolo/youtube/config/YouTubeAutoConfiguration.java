package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.properties.YouTubeRateLimitProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "youtube", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({
    YouTubeProperties.class,
    YouTubeRateLimitProperties.class
})
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
