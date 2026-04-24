package com.pluxurydolo.youtube.configuration;

import com.pluxurydolo.youtube.properties.YouTubeAuthProperties;
import com.pluxurydolo.youtube.properties.YouTubeEndpointProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    YouTubeAuthProperties.class,
    YouTubeEndpointProperties.class
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
