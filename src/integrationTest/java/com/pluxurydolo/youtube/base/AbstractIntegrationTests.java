package com.pluxurydolo.youtube.base;

import com.pluxurydolo.youtube.TestApplication;
import com.pluxurydolo.youtube.configuration.SchedulerTestConfiguration;
import com.pluxurydolo.youtube.configuration.SecretTestConfiguration;
import com.pluxurydolo.youtube.configuration.TokensTestConfiguration;
import com.pluxurydolo.youtube.configuration.YouTubeTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    SecretTestConfiguration.class,
    TokensTestConfiguration.class,
    SchedulerTestConfiguration.class,
    YouTubeTestConfiguration.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
