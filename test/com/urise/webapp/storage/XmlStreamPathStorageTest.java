package com.urise.webapp.storage;

import com.urise.webapp.util.storage.PathStorage;
import com.urise.webapp.util.storage.stream.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {

    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamSerializer()));
    }
}