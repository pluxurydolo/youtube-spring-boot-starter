package com.pluxurydolo.youtube.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeCoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NetHttpTransport netHttpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    @ConditionalOnMissingBean
    public GsonFactory gsonFactory() {
        return GsonFactory.getDefaultInstance();
    }
}
