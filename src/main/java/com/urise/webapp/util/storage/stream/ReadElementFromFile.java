package com.urise.webapp.util.storage.stream;

import java.io.IOException;

@FunctionalInterface
public interface ReadElementFromFile<T> {
   T readElementFromFile( ) throws IOException;
}
