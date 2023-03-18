package com.urise.webapp.util;

import com.urise.webapp.storage.SQLStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected File PROPS = new File("C:\\Users\\ptatara\\basejava\\config\\resumes.properties");
    private final Properties props = new Properties();
    private final File storageDir;
    private final Storage sqlStorage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SQLStorage(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException ex) {
            throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }

    public String getProperty(String nameProperty) {
        return props.getProperty(nameProperty);
    }

    public File getStorageDir() {
        return storageDir;
    }
}
