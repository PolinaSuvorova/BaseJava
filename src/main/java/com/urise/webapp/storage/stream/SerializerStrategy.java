package com.urise.webapp.storage.stream;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializerStrategy {
    void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
