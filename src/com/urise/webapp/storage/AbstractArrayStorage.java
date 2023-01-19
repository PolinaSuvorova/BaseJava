package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    static public final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    final public int size() {
        return size;
    }

    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Object  searchKey, Resume resume) {
        Integer index = (Integer) searchKey;
        updateResume(index, resume);

    }

    @Override
    protected void doSave(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполненно", resume.getUuid());
        }
        Integer index = getSearchKey(resume.getUuid());
        insertResume(index, resume);
        size++;
    }

    @Override
    protected Resume doGet(Object  searchKey) {
        Integer index = (Integer) searchKey;
        return storage[index];
    }

    @Override
    protected void doDelete(Object  searchKey) {
        Integer index = (Integer) searchKey;
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected boolean isExistKey(Object searchKey) {
        Integer index = (Integer) searchKey;
        return index >= 0;
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    final public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    protected abstract void updateResume(int index, Resume resume);
}
