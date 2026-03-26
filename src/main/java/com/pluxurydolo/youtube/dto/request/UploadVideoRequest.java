package com.pluxurydolo.youtube.dto.request;

import java.io.File;

public record UploadVideoRequest(
    File file,
    String title,
    String description,
    String[] tags
) {
}
