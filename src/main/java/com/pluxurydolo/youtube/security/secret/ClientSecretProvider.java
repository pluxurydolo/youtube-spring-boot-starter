package com.pluxurydolo.youtube.security.secret;

import java.io.InputStream;

public interface ClientSecretProvider {
    InputStream getClientSecret();
}
