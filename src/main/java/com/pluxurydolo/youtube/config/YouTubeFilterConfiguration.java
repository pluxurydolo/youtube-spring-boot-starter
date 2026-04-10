package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.filter.YouTubeRateLimitFilter;
import com.pluxurydolo.youtube.filter.YouTubeRequestParamValidationFilter;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.properties.YouTubeRateLimitProperties;
import com.pluxurydolo.youtube.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class YouTubeFilterConfiguration {

    @Bean
    public YouTubeRequestParamValidationFilter youTubeRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        YouTubeProperties youTubeProperties
    ) {
        return new YouTubeRequestParamValidationFilter(requestParamValidator, youTubeProperties);
    }

    @Bean
    public YouTubeRateLimitFilter youTubeRateLimitingFilter(
        AtomicInteger requestCounter,
        YouTubeProperties youTubeProperties,
        YouTubeRateLimitProperties youTubeRateLimitProperties
    ) {
        return new YouTubeRateLimitFilter(requestCounter, youTubeProperties, youTubeRateLimitProperties);
    }

    @Bean
    public AtomicInteger requestCounter() {
        return new AtomicInteger(0);
    }
}
