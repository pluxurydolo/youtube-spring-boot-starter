package com.pluxurydolo.youtube.dto.request;

public record UploadVideoRequest(
    byte[] bytes,
    String title,
    String description,
    String[] tags
) {
}
