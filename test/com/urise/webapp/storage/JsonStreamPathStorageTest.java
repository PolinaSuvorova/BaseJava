package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.JsonStreamSerializer;

public class JsonStreamPathStorageTest extends AbstractStorageTest {

    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(),
                new JsonStreamSerializer()));
    }
}
