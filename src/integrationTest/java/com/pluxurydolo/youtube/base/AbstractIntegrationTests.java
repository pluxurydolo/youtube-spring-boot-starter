package com.pluxurydolo.youtube.base;

import com.pluxurydolo.youtube.TestApplication;
import com.pluxurydolo.youtube.config.SchedulerTestConfig;
import com.pluxurydolo.youtube.config.SecretTestConfig;
import com.pluxurydolo.youtube.config.TokensTestConfig;
import com.pluxurydolo.youtube.config.ValidatorTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    SecretTestConfig.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "youtube.enabled=true",
    "youtube.application-name=app-name",
    "youtube.login.url=/app-name/v1/youtube/login",
    "youtube.redirect.uri=http://localhost:8888$/app-name/v1/youtube/login/redirect",
    "youtube.redirect.url=/app-name/v1/youtube/login/redirect",
    "youtube.refresh.url=/app-name/v1/youtube/refresh",
    "youtube.refresh.token.scheduler.cron=0 0 0 * * SUN",
    "youtube.refresh.token.scheduler.zone=Europe/Moscow",
    "youtube.rate-limit.threshold=5"
})
public abstract class AbstractIntegrationTests {
}
