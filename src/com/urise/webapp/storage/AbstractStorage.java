package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(String uuid, String fullName) {
        SK searchKey = getExistingSearchKey(uuid);
        Resume resume = new Resume(uuid, fullName);
        doUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExistKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExistKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getDataAsList();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    protected abstract List<Resume> getDataAsList();

    protected abstract boolean isExistKey(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doSave(Resume resume, SK searchKey);
    protected abstract Resume doGet(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doDelete(SK searchKey);

}
