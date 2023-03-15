package com.urise.webapp.util;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config  INSTANSE = new Config();
    protected static final File PROPS = new File("./config/resumes.properties");
    private Properties props = new Properties();
    private File storageDir;
    public static Config getInstance(){ return INSTANSE; }
    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException ex) {
           throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
