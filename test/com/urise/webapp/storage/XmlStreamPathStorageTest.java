package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {

    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamSerializer()));
    }
}