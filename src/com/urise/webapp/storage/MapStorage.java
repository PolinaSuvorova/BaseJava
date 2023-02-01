package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
         map.put( resume.getUuid( ), resume);
        //Resume resumeOld = (Resume) searchKey;
        //String uuidOld = resumeOld.getUuid();
        //map.remove(uuidOld);
        //map.put(resume.getUuid(),resume);
        //map.replace(resumeOld.getUuid(),resume);
    }

    @Override
    protected void doSave(Resume resume) {
        String uuid = resume.getUuid();
        map.put( uuid, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        map.remove(resume.getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getDataAsList() {
        Resume[] array =  map.values().toArray(new Resume[0]);
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
        return searchKey != null;
    }
}
