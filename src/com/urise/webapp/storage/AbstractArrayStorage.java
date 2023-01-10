package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    static protected final int RESUME_LIMIT = 10000;
    protected Resume[] storage = new Resume[RESUME_LIMIT];
    protected int size;

   final public int size() {
        return size;
    }

   final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    final public void update(String uuid, Resume resume) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateResume(index,resume);
    }

    final public void save(Resume resume) {
        if (size >= RESUME_LIMIT) {
            throw new StorageException("Хранилище переполненно", resume.getUuid());
        } else {
            String uuid = resume.getUuid();
            int index = getIndex(uuid);
            if (index < 0) {
                insertResume(index,resume);
                size++;
            } else {
                throw new ExistStorageException(uuid);
            }
        }
    }

    final public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage[index];
        }
    }

    final public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
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
