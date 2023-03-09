package com.urise.webapp.storage;

import com.urise.webapp.util.storage.PathStorage;
import com.urise.webapp.util.storage.stream.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR_TXT, new ObjectStreamSerializer()));
    }
}
