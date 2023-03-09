package com.urise.webapp.storage;

import com.urise.webapp.util.storage.PathStorage;
import com.urise.webapp.util.storage.stream.JsonStreamSerializer;

public class JsonStreamPathStorageTest extends AbstractStorageTest {

    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR_TXT, new JsonStreamSerializer()));
    }
}
