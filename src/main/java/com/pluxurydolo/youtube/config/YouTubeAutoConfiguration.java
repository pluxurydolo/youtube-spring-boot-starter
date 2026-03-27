package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.properties.YouTubeProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "youtube", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(YouTubeProperties.class)
@Import({
    YouTubeCoreConfiguration.class,
    YouTubeOAuthConfiguration.class,
    YouTubeWebConfiguration.class,
    YouTubeClientConfiguration.class,
    YouTubeUploadStepConfiguration.class
})
public class YouTubeAutoConfiguration {
}
