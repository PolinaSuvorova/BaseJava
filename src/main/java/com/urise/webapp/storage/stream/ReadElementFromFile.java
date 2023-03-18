package com.urise.webapp.storage.stream;

import java.io.IOException;

@FunctionalInterface
public interface ReadElementFromFile<T> {
    T readElementFromFile() throws IOException;
}
