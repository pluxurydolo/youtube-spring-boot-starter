package com.pluxurydolo.youtube.controller;

import com.pluxurydolo.youtube.base.AbstractControllerIntegrationTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class YouTubeOAuthControllerIntegrationTests extends AbstractControllerIntegrationTests {

    @Test
    void testLogin() {
        webTestClient.get()
            .uri("/app-name/v1/youtube/login")
            .exchange()
            .expectStatus().isFound()
            .expectHeader().location("requestUrl")
            .expectBody().isEmpty();
    }

    @Test
    void testRedirect() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/app-name/v1/youtube/login/redirect")
                .queryParam("code", "code")
                .build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("SUCCESS"));
    }

    @Test
    void testRefresh() {
        webTestClient.get()
            .uri("/app-name/v1/youtube/refresh-token")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("SUCCESS"));
    }
}
