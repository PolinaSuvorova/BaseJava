package com.urise.webapp.util.storage.stream;

import java.io.IOException;

@FunctionalInterface
interface WriteElementCollection<T> {
    void writeElementCollection(T element) throws IOException;
}
