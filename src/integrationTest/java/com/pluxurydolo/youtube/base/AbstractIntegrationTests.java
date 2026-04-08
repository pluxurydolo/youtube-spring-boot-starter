package com.pluxurydolo.youtube.base;

import com.pluxurydolo.youtube.TestApplication;
import com.pluxurydolo.youtube.config.SchedulerTestConfig;
import com.pluxurydolo.youtube.config.SecretTestConfig;
import com.pluxurydolo.youtube.config.TokensTestConfig;
import com.pluxurydolo.youtube.config.ValidatorTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    SecretTestConfig.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
