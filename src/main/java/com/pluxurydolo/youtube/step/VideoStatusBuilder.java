package com.pluxurydolo.youtube.step;

import com.google.api.services.youtube.model.VideoStatus;

public class VideoStatusBuilder {
    public VideoStatus build() {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("public");
        status.setSelfDeclaredMadeForKids(false);
        status.setEmbeddable(true);
        return status;
    }
}
