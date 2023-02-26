package com.urise.webapp.storage;

import java.io.File;

public class ObjectStreamFileStorage extends AbstractFileStorage{

    protected ObjectStreamFileStorage(File directory) {
        super(directory, new ObjectStreamStorage());
    }
}
