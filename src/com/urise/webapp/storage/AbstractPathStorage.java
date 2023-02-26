package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final AbstractObjectStreamStorage objStreamStorage;
    private int countFiles;

    protected AbstractPathStorage(String path, AbstractObjectStreamStorage objStreamStorage) {

        Objects.requireNonNull(path, "directory must not be null");
        directory = Paths.get(path);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(path + " is not " +
                    "directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(path + " is not " +
                    "readable/writable");
        }
        this.objStreamStorage = objStreamStorage;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);

        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        countFiles = 0;
        try {
            Files.list(directory).forEach(path -> countForEach());
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
        return countFiles;
    }

    private void countForEach() {
        countFiles++;
    }

    ;

    @Override
    protected List<Resume> getDataAsList() {
        List<Resume> list = new ArrayList<>();

        try {
            Files.list(directory).forEach(path -> list.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }

        return list;
    }

    @Override
    protected boolean isExistKey(Path searchKey) {
        return Files.exists(directory);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            objStreamStorage.doWrite(resume,
                    new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            doUpdate(Files.createFile(path), resume);

        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return objStreamStorage.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + "\\" + uuid + ".txt");
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.deleteIfExists(searchKey);
        } catch (IOException e) {
            throw new StorageException("I/O error", null, e);
        }
    }
}
