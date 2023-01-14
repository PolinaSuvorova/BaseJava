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
    protected void aUpdate(int index, Resume resume) {
        list.set(index, resume);
    }

    @Override
    protected void aSave(int index, Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume aGet(int index) {
        return list.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return list.indexOf(resume);
    }

    @Override
    public void aDelete(int index) {
        list.remove(index);
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
