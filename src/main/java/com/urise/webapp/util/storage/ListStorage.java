package com.urise.webapp.util.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected List<Resume> getDataAsList() {
        return new ArrayList<>(list);
    }

    @Override
    protected boolean isExistKey(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void doDelete(Integer index) {
        list.remove(doGet(index));
    }

    @Override
    public int size() {
        return list.size();
    }

}
