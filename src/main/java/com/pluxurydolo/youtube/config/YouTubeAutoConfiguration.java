package com.pluxurydolo.youtube.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "youtube", name = "enabled", havingValue = "true")
@Import({
    CoreConfiguration.class,
    OAuthConfiguration.class,
    WebConfiguration.class,
    ClientConfiguration.class,
    StepConfiguration.class
})
public class YouTubeAutoConfiguration {
}
