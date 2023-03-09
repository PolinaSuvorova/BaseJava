package com.urise.webapp.storage;

import com.urise.webapp.util.storage.PathStorage;
import com.urise.webapp.util.storage.stream.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR_TXT, new DataStreamSerializer()));
    }
}