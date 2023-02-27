package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.ObjectStreamSerializer;
import com.urise.webapp.storage.stream.PathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest( ) {
        super(new PathStorage(STORAGE_DIR_TXT, new ObjectStreamSerializer()));
    }
}
