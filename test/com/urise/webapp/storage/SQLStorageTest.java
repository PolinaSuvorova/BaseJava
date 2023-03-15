package com.urise.webapp.storage;

import com.urise.webapp.util.Config;
import com.urise.webapp.util.storage.SQLStorage;

public class SQLStorageTest extends AbstractStorageTest{
    public SQLStorageTest( ) {
        super( new SQLStorage(
                Config.getInstance().getProperty("db.url"),
                Config.getInstance().getProperty("db.user"),
                Config.getInstance().getProperty("db.password"))
        );
    }
}
