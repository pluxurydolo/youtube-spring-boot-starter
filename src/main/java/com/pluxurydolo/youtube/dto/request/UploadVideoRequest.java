package com.pluxurydolo.youtube.dto.request;

import java.util.List;

public record UploadVideoRequest(
    byte[] bytes,
    String title,
    String description,
    List<String> tags
) {
}
