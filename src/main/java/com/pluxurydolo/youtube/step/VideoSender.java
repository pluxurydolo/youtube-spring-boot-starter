package com.pluxurydolo.youtube.step;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.pluxurydolo.youtube.exception.YouTubeUploadException;
import org.springframework.core.io.InputStreamSource;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class VideoSender {
    private final MediaHttpUploaderProgressListener progressListener;

    public VideoSender(MediaHttpUploaderProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public Mono<Video> sendVideo(YouTube youTube, InputStreamSource multipartFile, List<String> parts, Video video) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamContent inputStreamContent = new InputStreamContent("video/mp4", inputStream);

            YouTube.Videos.Insert request = youTube.videos()
                .insert(parts, video, inputStreamContent);

            request.getMediaHttpUploader()
                .setDirectUploadEnabled(false)
                .setProgressListener(progressListener);

            return Mono.fromCallable(request::execute);
        } catch (IOException exception) {
            return Mono.error(new YouTubeUploadException(exception));
        }
    }
}
