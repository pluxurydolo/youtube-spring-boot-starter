package com.pluxurydolo.youtube.flow.upload;

import com.google.api.services.youtube.model.VideoStatus;

public class YouTubeVideoStatusBuilder {
    public VideoStatus build() {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("public");
        status.setSelfDeclaredMadeForKids(false);
        status.setEmbeddable(true);
        return status;
    }
}
