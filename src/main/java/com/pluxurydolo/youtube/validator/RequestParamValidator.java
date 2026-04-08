package com.pluxurydolo.youtube.validator;

import reactor.core.publisher.Mono;

public interface RequestParamValidator {
    Mono<ValidationResult> validate(String accessToken);
}
