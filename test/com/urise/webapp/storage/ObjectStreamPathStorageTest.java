package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR_TXT, new ObjectStreamSerializer()));
    }
}
