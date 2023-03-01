package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.stream.SerializerStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializerStrategy objStreamStorage;

    public FileStorage(File directory, SerializerStrategy objStreamStorage) {
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
        File[] listFiles = getCheckedListFiles( );
        return listFiles.length;
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
        } catch (IOException e) {
            throw new StorageException("I/O error", directory.getName(), e);
        }
        doUpdate(file, resume);
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
        return new File(directory, uuid );
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("I/O error", searchKey.getName());
        }
    }

    private File[] getCheckedListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Рathname does not denote a directory, or if an I/O error", directory.getAbsolutePath());
        }
        return files;
    }

}