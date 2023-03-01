package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.exception.model.Resume;
import com.urise.webapp.storage.stream.SerializerStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializerStrategy objStreamStorage;

    public PathStorage(String path, SerializerStrategy objStreamStorage) {

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
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    protected List<Resume> getDataAsList() {
        List<Resume> list = new ArrayList<>();
        getFilesList().forEach(path -> list.add(doGet(path)));
        return list;
    }

    @Override
    protected boolean isExistKey(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            objStreamStorage.doWrite(resume,
                    new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Error update file", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) throws StorageException {
        Path file;
        try {
            file = Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Error create File " + resume.getUuid(),
                    path.getFileName().toString(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return objStreamStorage.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Error read file", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.deleteIfExists(searchKey);
        } catch (IOException e) {
            throw new StorageException("Error delete File", searchKey.getFileName().toString(), e);
        }
    }
}
