package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract void aUpdate(int index, Resume resume);

    protected abstract void aSave(int index, Resume resume);

    protected abstract Resume aGet(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void aDelete(int index);

    @Override
    public void update(String uuid, Resume resume) {
        int index = getIndexByUuid(uuid);
        aUpdate(index, resume);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        aSave(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndexByUuid(uuid);
        return aGet(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndexByUuid(uuid);
        aDelete(index);
    }

    private int getIndexByUuid(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

}
