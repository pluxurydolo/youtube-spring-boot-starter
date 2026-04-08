package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.filter.RequestParamValidationFilter;
import com.pluxurydolo.youtube.properties.YouTubeProperties;
import com.pluxurydolo.youtube.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeFilterConfiguration {

    @Bean
    public RequestParamValidationFilter requestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        YouTubeProperties youTubeProperties
    ) {
        return new RequestParamValidationFilter(requestParamValidator, youTubeProperties);
    }
}
