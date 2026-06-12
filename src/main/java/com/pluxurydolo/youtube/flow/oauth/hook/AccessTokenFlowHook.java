package com.pluxurydolo.youtube.flow.oauth.hook;

import reactor.core.publisher.Mono;

public interface AccessTokenFlowHook {
    Mono<String> doAfter();

    Mono<String> handleException(Throwable throwable);
}
