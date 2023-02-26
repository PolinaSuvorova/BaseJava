package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;
    private final AbstractObjectStreamStorage objStreamStorage;

    protected AbstractFileStorage(File directory, AbstractObjectStreamStorage objStreamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not " +
                    "readable/writable");
        }
        this.directory = directory;
        this.objStreamStorage = objStreamStorage;
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
            objStreamStorage.doWrite(resume,
                    new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error", directory.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doUpdate(file, resume);

        } catch (IOException e) {
            throw new StorageException("I/O error", directory.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return objStreamStorage.doRead(
                    new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error", searchKey.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid + ".txt");
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
            throw new StorageException("I/O Error", directory.getAbsolutePath());
        }
        return files;
    }

}
