package com.pluxurydolo.youtube.security.secret;

import java.io.InputStream;

public interface YouTubeClientSecretProvider {
    InputStream getClientSecret();
}
