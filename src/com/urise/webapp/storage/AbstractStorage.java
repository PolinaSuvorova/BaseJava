package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private static final Comparator<Resume> SORT_RESUME =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(String uuid, String fullName) {
        Object searchKey = getExistingSearchKey(uuid);
        Resume resume = new Resume(uuid, fullName);
        doUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        if (getNotExistingSearchKey(resume.getUuid())) {
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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getDataAsList();
        list.sort(SORT_RESUME);
        return list;
    }

    protected abstract List<Resume> getDataAsList();

    protected abstract boolean isExistKey(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doSave(Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doDelete(Object searchKey);

}
