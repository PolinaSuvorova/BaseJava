package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.ObjectStreamSerializer;

public class XmlStreamPathStorageTest  extends AbstractStorageTest {

    public XmlStreamPathStorageTest( ) {
        super(new PathStorage(STORAGE_DIR_TXT, new XmlStreamSerializer()));
    }
}