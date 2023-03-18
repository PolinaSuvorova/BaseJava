package com.urise.webapp.storage;

import com.urise.webapp.util.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.getInstance().getSqlStorage());
    }
}
