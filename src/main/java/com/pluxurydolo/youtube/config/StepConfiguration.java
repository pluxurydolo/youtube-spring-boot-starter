package com.pluxurydolo.youtube.config;

import com.pluxurydolo.youtube.step.VideoBuilder;
import com.pluxurydolo.youtube.step.VideoSender;
import com.pluxurydolo.youtube.step.VideoSnippetBuilder;
import com.pluxurydolo.youtube.step.VideoStatusBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepConfiguration {

    @Bean
    public VideoSnippetBuilder videoSnippetBuilder() {
        return new VideoSnippetBuilder();
    }

    @Bean
    public VideoStatusBuilder videoStatusBuilder() {
        return new VideoStatusBuilder();
    }

    @Bean
    public VideoBuilder videoBuilder() {
        return new VideoBuilder();
    }

    @Bean
    public VideoSender videoSender() {
        return new VideoSender();
    }
}
