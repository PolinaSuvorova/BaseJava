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
    protected void aUpdate(int index, Resume resume) {
        updateResume(index, resume);
    }

    @Override
    protected void aSave(int index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполненно", resume.getUuid());
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    @Override
    protected Resume aGet(int index) {
        //  int index = getIndex(uuid);
        //   if (index < 0) {
        //       throw new NotExistStorageException(uuid);
        //    } else {
        return storage[index];
        //    }
    }

    @Override
    protected void aDelete(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    final public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    protected abstract void updateResume(int index, Resume resume);
}
