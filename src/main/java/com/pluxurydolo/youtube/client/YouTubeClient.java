package com.pluxurydolo.youtube.client;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Videos.Insert;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.pluxurydolo.youtube.dto.MultipartFileWrapper;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
import com.pluxurydolo.youtube.util.YouTubeInstanceBuilder;
import com.pluxurydolo.youtube.util.YouTubeUploadProgressListener;
import org.springframework.core.io.InputStreamSource;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class YouTubeClient {
    private final YouTubeInstanceBuilder youTubeInstanceBuilder;

    public YouTubeClient(YouTubeInstanceBuilder youTubeInstanceBuilder) {
        this.youTubeInstanceBuilder = youTubeInstanceBuilder;
    }

    public Mono<String> uploadVideo(File file, String title, String description, String[] tags) {
        List<String> parts = List.of("snippet", "status");
        Video video = video(title, description, tags);
        InputStreamSource multipartFile = new MultipartFileWrapper(file);

        return youTubeInstanceBuilder.build()
            .flatMap(youTube -> sendVideo(youTube, multipartFile, title, parts, video))
            .subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> sendVideo(
        YouTube youTube,
        InputStreamSource multipartFile,
        String title,
        List<String> parts,
        Video video
    ) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamContent mediaContent = new InputStreamContent("video/mp4", inputStream);

            Insert request = youTube.videos()
                .insert(parts, video, mediaContent);

            request.getMediaHttpUploader()
                .setDirectUploadEnabled(false)
                .setProgressListener(new YouTubeUploadProgressListener());

            request.execute();
        } catch (IOException exception) {
            return Mono.error(new YouTubeUploadException(exception));
        }

        return Mono.just(title);
    }

    private static Video video(String title, String description, String[] tags) {
        VideoSnippet videoSnippet = videoSnippet(title, description, tags);
        VideoStatus status = videoStatus();

        Video video = new Video();
        video.setSnippet(videoSnippet);
        video.setStatus(status);

        return video;
    }

    private static VideoSnippet videoSnippet(String title, String description, String[] tags) {
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setCategoryId("24");
        snippet.setTags(Arrays.asList(tags));
        return snippet;
    }

    private static VideoStatus videoStatus() {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("public");
        status.setSelfDeclaredMadeForKids(false);
        status.setEmbeddable(true);
        return status;
    }
}
