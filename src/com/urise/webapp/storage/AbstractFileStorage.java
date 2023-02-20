package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not " +
                    "readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getDataAsList() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        if ( files == null ){
            throw new StorageException("Get error", null);
        }
        for (File file : files){
            list.add( doGet(file) );
        }
        return list;
    }

    @Override
    protected boolean isExistKey(Object searchKey) {
        File file = (File) searchKey;
        return file.exists();
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        File file = (File) searchKey;
        doWrite(resume, file);
    }

    @Override
    protected void doSave(Resume resume) {
        try {
            directory.createNewFile();
            doWrite(resume, directory);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.getName(), e);
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        File file = (File) searchKey;
        return doRead( file );
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doDelete(Object searchKey) {
        File file = (File) searchKey;
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", null);
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("IO error", null);
        }
        return list.length;
    }
    protected abstract void doWrite(Resume r, File file);
    protected abstract Resume doRead(File file);
}
