package com.pluxurydolo.youtube.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MultipartFileWrapper implements MultipartFile {
    private final String name;
    private final String originalFilename;
    private final Path filePath;
    private final long size;

    public MultipartFileWrapper(File file) {
        this.name = file.getName();
        this.originalFilename = file.getName();
        this.filePath = file.toPath();
        this.size = file.length();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return "video/mp4";
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return readAllBytes(filePath);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return newInputStream(filePath);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        copy(filePath, dest.toPath(), REPLACE_EXISTING);
    }
}
