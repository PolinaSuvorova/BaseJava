package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected boolean isExistKey(Object searchKey) {
        Integer index = (Integer) searchKey;
        return index >= 0;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected void doSave( Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return list.get((Integer) index);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return list.indexOf(resume);
    }

    @Override
    public void doDelete( Object index) {
        list.remove(doGet(index));
    }


    @Override
    public Resume[] getAll() {
        return list.toArray( new Resume[0] );
    }

    @Override
    public int size() {
        return list.size();
    }

}
