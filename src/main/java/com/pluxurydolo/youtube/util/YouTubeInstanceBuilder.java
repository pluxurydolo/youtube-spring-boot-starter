package com.pluxurydolo.youtube.util;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.auth.http.HttpCredentialsAdapter;
import com.pluxurydolo.youtube.dto.Tokens;
import com.pluxurydolo.youtube.security.CredentialsRetriever;
import com.pluxurydolo.youtube.security.token.AbstractTokenRetriever;
import reactor.core.publisher.Mono;

public class YouTubeInstanceBuilder {
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final CredentialsRetriever credentialsRetriever;
    private final NetHttpTransport netHttpTransport;
    private final GsonFactory gsonFactory;
    private final String applicationName;

    public YouTubeInstanceBuilder(
        AbstractTokenRetriever abstractTokenRetriever,
        CredentialsRetriever credentialsRetriever,
        NetHttpTransport netHttpTransport,
        GsonFactory gsonFactory,
        String applicationName
    ) {
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.credentialsRetriever = credentialsRetriever;
        this.netHttpTransport = netHttpTransport;
        this.gsonFactory = gsonFactory;
        this.applicationName = applicationName;
    }

    public Mono<YouTube> build() {
        return abstractTokenRetriever.retrieve()
            .map(Tokens::refreshToken)
            .flatMap(credentialsRetriever::retrieve)
            .map(this::youTube);
    }

    private YouTube youTube(HttpCredentialsAdapter credentialsAdapter) {
        return new YouTube.Builder(netHttpTransport, gsonFactory, credentialsAdapter)
            .setApplicationName(applicationName)
            .build();
    }
}
