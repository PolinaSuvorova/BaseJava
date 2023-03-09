package com.urise.webapp.storage;

import com.urise.webapp.util.storage.FileStorage;
import com.urise.webapp.util.storage.stream.ObjectStreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
