package com.pluxurydolo.youtube.dto.request;

import java.util.List;

public record PublishVideoRequest(
    byte[] bytes,
    String title,
    String description,
    List<String> tags
) {
}
