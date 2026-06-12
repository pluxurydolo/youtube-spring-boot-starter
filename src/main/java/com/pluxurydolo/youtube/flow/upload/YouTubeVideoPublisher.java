package com.pluxurydolo.youtube.flow.upload;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.request.PublishVideoRequest;
import com.pluxurydolo.youtube.exception.YouTubePublishVideoException;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class YouTubeVideoPublisher {
    private final YouTubeInstanceBuilder youTubeInstanceBuilder;
    private final YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder;
    private final YouTubeVideoStatusBuilder youTubeVideoStatusBuilder;
    private final YouTubeVideoBuilder youTubeVideoBuilder;
    private final MediaHttpUploaderProgressListener progressListener;

    public YouTubeVideoPublisher(
        YouTubeInstanceBuilder youTubeInstanceBuilder,
        YouTubeVideoSnippetBuilder youTubeVideoSnippetBuilder,
        YouTubeVideoStatusBuilder youTubeVideoStatusBuilder,
        YouTubeVideoBuilder youTubeVideoBuilder,
        MediaHttpUploaderProgressListener progressListener
    ) {
        this.youTubeInstanceBuilder = youTubeInstanceBuilder;
        this.youTubeVideoSnippetBuilder = youTubeVideoSnippetBuilder;
        this.youTubeVideoStatusBuilder = youTubeVideoStatusBuilder;
        this.youTubeVideoBuilder = youTubeVideoBuilder;
        this.progressListener = progressListener;
    }

    public Mono<String> publish(PublishVideoRequest request) {
        String title = request.title();
        byte[] bytes = request.bytes();

        List<String> parts = List.of("snippet", "status");
        Video video = buildVideo(request);

        return youTubeInstanceBuilder.build()
            .flatMap(youTube -> publishVideo(bytes, youTube, parts, video))
            .thenReturn(title);
    }

    private Video buildVideo(PublishVideoRequest request) {
        String title = request.title();
        String description = request.description();

        String[] tags = request.tags()
            .toArray(new String[0]);

        VideoSnippet videoSnippet = youTubeVideoSnippetBuilder.build(title, description, tags);
        VideoStatus videoStatus = youTubeVideoStatusBuilder.build();
        return youTubeVideoBuilder.build(videoSnippet, videoStatus);
    }

    private Mono<Video> publishVideo(byte[] bytes, YouTube youTube, List<String> parts, Video video) {
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            InputStreamContent inputStreamContent = new InputStreamContent("video/mp4", inputStream);

            YouTube.Videos.Insert request = youTube.videos()
                .insert(parts, video, inputStreamContent);

            request.getMediaHttpUploader()
                .setDirectUploadEnabled(false)
                .setProgressListener(progressListener);

            return Mono.fromCallable(request::execute);
        } catch (IOException exception) {
            return Mono.error(new YouTubePublishVideoException(exception));
        }
    }
}
