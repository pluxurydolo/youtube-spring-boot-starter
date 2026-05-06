package com.pluxurydolo.youtube.flow.upload;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

public class YouTubeVideoBuilder {
    public Video build(VideoSnippet videoSnippet, VideoStatus videoStatus) {
        Video video = new Video();
        video.setSnippet(videoSnippet);
        video.setStatus(videoStatus);
        return video;
    }
}
