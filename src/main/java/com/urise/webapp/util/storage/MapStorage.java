package com.urise.webapp.util.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<Object> {
    protected Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        String uuid = resume.getUuid();
        map.put(uuid, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
    //protected Object getSearchKey(String uuid) {
    //    return map.get(uuid);
    //}

    @Override
    protected void doDelete(Object searchKey) {
        //    Resume resume = (Resume) searchKey;
        String uuid = (String) searchKey;
        map.remove(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getDataAsList() {
        Resume[] array = map.values().toArray(new Resume[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }
    // @Override
    // public Resume[] getAll() {
    //     return map.values().toArray(new Resume[0]);
    // }

    @Override
    public int size() {
        return map.size();
    }

    protected boolean isExistKey(Object searchKey) {
        return map.containsKey((String) searchKey);
    }
}
