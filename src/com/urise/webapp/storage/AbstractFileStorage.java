package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
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
    public void clear() {
            File[] files = getCheckedListFiles();
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

    @Override
    protected List<Resume> getDataAsList() {
        List<Resume> list = new ArrayList<>();
        try {
            File[] files = getCheckedListFiles();
            for (File file : files) {
                list.add(doGet(file));
            }
        } catch (StorageException e) {
            return null;
        }
        return list;
    }

    @Override
    protected boolean isExistKey(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            doWrite(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("I/O error", directory.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume) {
        try {
           if ( directory.createNewFile() ) {
               doWrite(resume, directory);
           }
        } catch (IOException e) {
            throw new StorageException("I/O error", directory.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return doRead(searchKey);
        } catch (IOException e) {
            throw new StorageException("I/O error", searchKey.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("I/O error", searchKey.getName());
        }
    }

    private File[] getCheckedListFiles() throws StorageException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("I/O Error",directory.getAbsolutePath());
        }
        return files;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
