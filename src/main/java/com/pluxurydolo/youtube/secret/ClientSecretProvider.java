package com.pluxurydolo.youtube.secret;

import java.io.InputStream;

public interface ClientSecretProvider {
    InputStream getClientSecret();
}
