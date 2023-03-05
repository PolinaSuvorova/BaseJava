package com.urise.webapp.storage.stream;

import java.io.IOException;

@FunctionalInterface
interface WriteElementCollection<T> {
    void writeElementCollection(T element) throws IOException;
}
