package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.filter.YouTubeRequestParamValidationFilter;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.validator.RequestParamValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public YouTubeRequestParamValidationFilter youTubeRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        YouTubeProperties youTubeProperties
    ) {
        return new YouTubeRequestParamValidationFilter(requestParamValidator, youTubeProperties);
    }
}
