package com.pluxurydolo.youtube.client;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.MultipartFileWrapper;
import com.pluxurydolo.youtube.dto.request.UploadVideoRequest;
import com.pluxurydolo.youtube.step.VideoBuilder;
import com.pluxurydolo.youtube.step.VideoSender;
import com.pluxurydolo.youtube.step.VideoSnippetBuilder;
import com.pluxurydolo.youtube.step.VideoStatusBuilder;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import org.springframework.core.io.InputStreamSource;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.List;

public class YouTubeClient {
    private final YouTubeInstanceBuilder youTubeInstanceBuilder;
    private final VideoSnippetBuilder videoSnippetBuilder;
    private final VideoStatusBuilder videoStatusBuilder;
    private final VideoBuilder videoBuilder;
    private final VideoSender videoSender;

    public YouTubeClient(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        VideoSnippetBuilder videoSnippetBuilder,
        VideoStatusBuilder videoStatusBuilder,
        VideoBuilder videoBuilder,
        VideoSender videoSender
    ) {
        this.youTubeInstanceBuilder = youTubeInstanceBuilder;
        this.videoSnippetBuilder = videoSnippetBuilder;
        this.videoStatusBuilder = videoStatusBuilder;
        this.videoBuilder = videoBuilder;
        this.videoSender = videoSender;
    }

    public Mono<String> uploadVideo(UploadVideoRequest uploadVideoRequest) {
        File file = uploadVideoRequest.file();
        String title = uploadVideoRequest.title();
        String description = uploadVideoRequest.description();
        String[] tags = uploadVideoRequest.tags();

        VideoSnippet videoSnippet = videoSnippetBuilder.build(title, description, tags);
        VideoStatus videoStatus = videoStatusBuilder.build();
        Video video = videoBuilder.build(videoSnippet, videoStatus);

        List<String> parts = List.of("snippet", "status");
        InputStreamSource multipartFile = new MultipartFileWrapper(file);

        return youTubeInstanceBuilder.build()
            .flatMap(youTube -> videoSender.sendVideo(youTube, multipartFile, parts, video))
            .thenReturn(title)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
