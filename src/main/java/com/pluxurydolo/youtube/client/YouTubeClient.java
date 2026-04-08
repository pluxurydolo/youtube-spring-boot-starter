package com.pluxurydolo.youtube.client;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.MultipartFileWrapper;
import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.step.YouTubeVideoBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoUploader;
import com.pluxurydolo.youtube.step.YouTubeVideoSnippetBuilder;
import com.pluxurydolo.youtube.step.YouTubeVideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.core.io.InputStreamSource;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.List;

public class YouTubeClient {
    private final YouTubeInstanceBuilder youTubeInstanceBuilder;
    private final YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder;
    private final YouTubeVideoStatusBuilder youTubeVideoStatusBuilder;
    private final YouTubeVideoBuilder youTubeVideoBuilder;
    private final YouTubeVideoUploader youTubeVideoUploader;

    public YouTubeClient(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder,
        YouTubeVideoStatusBuilder youTubeVideoStatusBuilder,
        YouTubeVideoBuilder youTubeVideoBuilder,
        YouTubeVideoUploader youTubeVideoUploader
    ) {
        this.youTubeInstanceBuilder = youTubeInstanceBuilder;
        this.youTubeVideoSnippetBuilder = youTubeVideoSnippetBuilder;
        this.youTubeVideoStatusBuilder = youTubeVideoStatusBuilder;
        this.youTubeVideoBuilder = youTubeVideoBuilder;
        this.youTubeVideoUploader = youTubeVideoUploader;
    }

    public Mono<String> uploadVideo(UploadVideoRequest request) {
        File file = request.file();
        String title = request.title();
        String description = request.description();
        String[] tags = request.tags();

        VideoSnippet videoSnippet = youTubeVideoSnippetBuilder.build(title, description, tags);
        VideoStatus videoStatus = youTubeVideoStatusBuilder.build();
        Video video = youTubeVideoBuilder.build(videoSnippet, videoStatus);

        List<String> parts = List.of("snippet", "status");
        InputStreamSource multipartFile = new MultipartFileWrapper(file);

        return youTubeInstanceBuilder.build()
            .flatMap(youTube -> youTubeVideoUploader.upload(youTube, multipartFile, parts, video))
            .thenReturn(title)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
