package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(String uuid, Resume resume) {
        Object searchKey = getExistingSearchKey(uuid);
        doUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        if (getNotExistingSearchKey(resume.getUuid())){
          doSave(resume);
        }

    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExistKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private boolean getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExistKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return true;
    }

    protected boolean isExistKey(Object searchKey) {
        Integer index = (Integer) searchKey;
        return index >= 0;
    }

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doSave( Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void doDelete(Object searchKey);
}
